JMeter Result Analysis Plugin
======================
[afranken/jmeter-analysis-maven-plugin](https://github.com/afranken/jmeter-analysis-maven-plugin)

修改了index.html

Usage Example
-------------

    <plugin>
      <groupId>com.lazerycode.jmeter</groupId>
      <artifactId>jmeter-analysis-maven-plugin</artifactId>
      <executions>
        <execution>
          <goals>
            <goal>analyze</goal>
          </goals>
          <configuration>
            <!--
            An AntPath-Style pattern matching a JMeter XML result file to analyze. Must be a fully qualified path.
            File may be GZiped, must end in .gz then.

            Default: not set.
            Required.
            -->
            <source>${project.build.directory}/**/*.jtl</source>

            <!--
            directory where to store analysis result files.

            Default: ${project.build.directory}
            Required.
            -->
            <targetDirectory>${project.build.directory}/results</targetDirectory>

            <!--
            Build failed if source directory is not found.

            Default: true
            -->
            <sourceDirFailed>true</sourceDirFailed>

            <!--
            Check analysis result files. If threshold is not correct, maven build failed.

            Default: not set.
            -->
            <checkResult>
              <!-- Optional : check throughput. -->
              <throughput>
                <!-- Default: -1 (disabling) -->
                <threshold>-1</threshold>

                <!-- Default: UPPER_LOWER_TOLERANCE
                Values could be : 
                * UPPER :                 minValue = threshold, maxValue = Double.MAX_VALUE
                * LOWER :                 minValue = 0, maxValue = threshold
                * UPPER_TOLERANCE :       minValue = threshold, maxValue = (threshold + (threshold * tolerance / 100))
                * LOWER_TOLERANCE :       minValue = (threshold - (threshold * tolerance / 100)), maxValue = threshold
                * UPPER_LOWER_TOLERANCE : minValue = (threshold - (threshold * tolerance / 100)), maxValue = (threshold + (threshold * tolerance / 100))
                * EQUALS :                minValue = maxValue = threshold

                If throughput result test is between minValue and maxValue, maven build is OK otherwise, build failed.
                -->
                <toleranceDirection>UPPER_LOWER_TOLERANCE</toleranceDirection>

                <!-- Default: 5 (percent)
                Used for calculate min et max values.
                -->
                <tolerance>5</tolerance>
              </throughput>
              <!-- Optional : check percent errors. -->
              <errors>
                <!-- As above -->
                <threshold>-1</threshold>
                <toleranceDirection>UPPER_LOWER_TOLERANCE</toleranceDirection>
                <tolerance>5</tolerance>
              </errors>
            </checkResult>

            <!--
            Request groups as a mapping from "group name" to "ant pattern".
            A request uri that matches an ant pattern will be associated with the group name.
            Request details, charts and CSV files are generated per requestGroup.

            The order is honored, a sample will be added to the first matching pattern. So it's possible
            to define various patterns and one catch all pattern.

            If not set, the threadgroup name of the request will be used.

            Default: not set.
            -->
            <requestGroups>
              <requestGroup>
                <name>pages</name>
                <pattern>/page/**</pattern>
                <!-- Optional -->
                <checkResult>
                  <!-- As above -->
                </checkResult>
              </requestGroup>
              <requestGroup>
                <name>binaries</name>
                <pattern>/binary/**</pattern>
                <!-- Optional -->
                <checkResult>
                  <!-- As above -->
                </checkResult>
              </requestGroup>
            </requestGroups>

            <!--
            Change default value for generating charts.

            Default: width=950, height=500
            -->
            <configurationCharts>
              <width>950</width>
              <height>500</height>
            </configurationCharts>

            <!--
            Maximum number of samples to keep (in main memory) before compressing. -1 disabling compression.

            Default: 50000
            -->
            <maxSamples>50000</maxSamples>

            <!--
            If set to true, the directory structure relative to source will be preserved during output.

            Default: false
            -->
            <preserveDirectories>false</preserveDirectories>

            <!--
            Set<String> of sample names that should be processed when analysing a results file.

            Default: sample, httpSample
            -->
            <sampleNames>
              <sampleName>sample</sampleName>
              <sampleName>httpSample</sampleName>
            </sampleNames>

            <!--
            If set to true will process all files found by the pattern defined in <source>.
            If set to false (the default) it will only process the first file found.

            *SETTING TO TRUE IS NOT RECOMMENDED*
            It can substantially impact performance, if you do this you do it at your own risk!

            Default: false
            -->
            <processAllFilesFound>false</processAllFilesFound>

            <!--
            Template directory where custom freemarker templates are stored.
            Freemarker templates are used for all generated output. (CSV files, HTML files, console output)
            Templates must be stored in one of the following three subfolders of the templateDirectory:

            csv, html, text

            The entry template must be called "main.ftl".

            For example,
            templateDirectory/text/main.ftl will be used for generating the console output.

            Default: not set.
            -->
            <templateDirectory>${project.basedir}/src/main/resources/</templateDirectory>

            <!--
            Mapping from resource URL to file name. Every resource will be downloaded and stored in 'targetDirectory'
            with the given filename. Tokens "_FROM_" and "_TO_" can be used as placeholders. These placeholders will
            be replaced by timestamps of execution interval (formatted as ISO8601, e.g. '20111216T145509+0100').

            Default: not set.
            -->
            <remoteResources>
              <property>
                <name>http://yourhost/path?from=_FROM_&amp;to=_TO_</name>
                <value>my_resource.txt</value>
              </property>
            </remoteResources>

            <!--
            Specify custom date format for resources not supporting ISO8601.
			
            Default IOS8601
            -->
            <remoteResourcesFromUntilDateFormat>HH:mm_yyyyMMdd</remoteResourcesFromUntilDateFormat>
			
            <!--
            List of writers that handle all output of the plugin.
           Defaults to:
           * com.lazerycode.jmeter.analyzer.writer.ChartWriter (generates detailed charts as PNGs),
           * com.lazerycode.jmeter.analyzer.writer.DetailsToCsvWriter (generates CSV files for every request group),
           * com.lazerycode.jmeter.analyzer.writer.DetailsToHtmlWriter (generates HTML files for every request group),
           * com.lazerycode.jmeter.analyzer.writer.HtmlWriter (generates an HTML overview file),
           * com.lazerycode.jmeter.analyzer.writer.SummaryTextToFileWriter (generates a TXT overview file),
           * com.lazerycode.jmeter.analyzer.writer.SummaryTextToStdOutWriter (generates overview output to stdout)

           If one of those should be deactivated or a new com.lazerycode.jmeter.analyzer.writer.Writer implementation should be added,
           all desired writers need to be configured!
            -->
            <!--<writers>-->
              <!--<com.lazerycode.jmeter.analyzer.writer.SummaryTextToStdOutWriter/>-->
              <!--<com.lazerycode.jmeter.analyzer.writer.SummaryTextToFileWriter/>-->
              <!--<com.lazerycode.jmeter.analyzer.writer.HtmlWriter/>-->
              <!--<com.lazerycode.jmeter.analyzer.writer.DetailsToCsvWriter/>-->
              <!--<com.lazerycode.jmeter.analyzer.writer.DetailsToHtmlWriter/>-->
              <!--<com.lazerycode.jmeter.analyzer.writer.ChartWriter/>-->
            <!--</writers>-->

          </configuration>
        </execution>
      </executions>
    </plugin>
