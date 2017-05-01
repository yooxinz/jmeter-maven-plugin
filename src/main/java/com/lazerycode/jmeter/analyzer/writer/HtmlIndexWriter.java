package com.lazerycode.jmeter.analyzer.writer;

import static com.lazerycode.jmeter.analyzer.config.Environment.ENVIRONMENT;
import static com.lazerycode.jmeter.analyzer.util.FileUtil.initializeFile;
import static com.lazerycode.jmeter.analyzer.util.FileUtil.urlEncode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lazerycode.jmeter.analyzer.parser.AggregatedResponses;
import com.lazerycode.jmeter.analyzer.statistics.IndexObject;
import com.lazerycode.jmeter.analyzer.statistics.Samples;
import org.springframework.core.io.Resource;

import com.google.common.annotations.VisibleForTesting;
import com.lazerycode.jmeter.analyzer.util.TemplateUtil;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Index
 * {@link com.lazerycode.jmeter.analyzer.AnalyzeMojo#requestGroups RequestGroups} as a HTML file
 */
public class HtmlIndexWriter {

    private static final String ROOT_TEMPLATE = "html/index.ftl";

    private String fileName = "index.html";

    /**
     * Render results as text to a file
     *
     * @throws java.io.IOException
     * @throws freemarker.template.TemplateException
     *
     */
    public void write(Resource[] resultDataFiles,Map<String,Map<String, AggregatedResponses>> datas) throws IOException, TemplateException {

        Map<String, Object> self = custom(resultDataFiles,datas);

        java.io.Writer out = getWriter(getFile(fileName));

        renderText(self, getRootTemplate(), out);

        out.flush();
        out.close();
    }

    private Map<String,Object> custom(Resource[] resultDataFiles,Map<String,Map<String, AggregatedResponses>> datas) {
        List<IndexObject> tests = new ArrayList<IndexObject>(resultDataFiles.length);
        boolean success=true;
        String testResult;
        int total=0;
        int successInt=0;
        int fail=0;



        for (int i=0;i<resultDataFiles.length;i++) {
            Resource resource=resultDataFiles[i];
            if(!resource.getFilename().isEmpty() && resource.getFilename().lastIndexOf('.') > -1) {
                total++;
                for (Map.Entry<String, AggregatedResponses> entry : datas.get(resource.getFilename().substring(0, resource.getFilename().lastIndexOf('.'))+".jtl").entrySet()) {

                    AggregatedResponses aggregatedResponses = entry.getValue();
                    aggregatedResponses.getDurationByUri();

                    Map<String, Samples> data;
                    data = aggregatedResponses.getDurationByUri();

                    for (Map.Entry<String, Samples> samplesEntry : data.entrySet()) {
                        if(samplesEntry.getValue().getErrorsCount()>0){
                            success=false;
                            break;
                        }

                    }
                    if(success){
                        successInt++;
                        testResult="成功";

                    }else {
                        fail++;
                        testResult="失败";
                    }
                    tests.add(new IndexObject(resource.getFilename().substring(0, resource.getFilename().lastIndexOf('.')),testResult));

                }


            }
        }

        tests.get(0).setTotal(total);
        tests.get(0).setSuccess(successInt);
        tests.get(0).setFail(fail);
        Map<String, Object> self = new HashMap<String, Object>();
        self.put("tests", tests);
        return self;
    }

    //--------------------------------------------------------------------------------------------------------------------
    protected File getFile(String name) throws IOException {
        return initializeFile(ENVIRONMENT.getTargetDirectory(), name, null);
    }

    /**
     * @return the relative path to the root Freemarker template
     */

    protected String getRootTemplate() {
        return ROOT_TEMPLATE;
    }

    //--------------------------------------------------------------------------------------------------------------------

    @VisibleForTesting
    protected java.io.Writer getWriter(File file) throws IOException {
        return new FileWriter(file);
    }

    /**
     * Render given {@link com.lazerycode.jmeter.analyzer.parser.AggregatedResponses testResults} as text
     *
     * @param rootTemplate the template that Freemarker starts rendering with
     * @param out         output to write to
     * @throws java.io.IOException
     * @throws freemarker.template.TemplateException
     *
     */
    protected void renderText(Map<String, ?> self, String rootTemplate,
            java.io.Writer out) throws IOException, TemplateException {

        Map<String, Object> rootMap = TemplateUtil.getRootMap(self);
        rootMap.put("SUMMARY_FILE_NAME", fileName);

        Template root = TemplateUtil.getTemplate(rootTemplate);

        // Merge data-model with template
        root.process(rootMap, out);
    }
}
