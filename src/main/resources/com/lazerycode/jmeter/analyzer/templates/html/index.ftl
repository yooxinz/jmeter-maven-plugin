<#ftl/>
<#-- @ftlvariable name="self" type="java.util.Map<java.lang.String, java.util.List<java.lang.String>>" -->
<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html>
<html>
<head>
  <title>JMeter Test Results</title>
  <meta charset="utf-8">
  <style type="text/css">
    body {
      font: normal verdana, arial, helvetica;
      color: #000000;
    }

    table {
      border-collapse: collapse;
    }

    table tr td, table tr th {
    }

    table tr th {
      font-weight: bold;
      text-align: left;
      background: #a6caf0;
      white-space: nowrap;
    }

    table tr td {
      background: #eeeee0;
      white-space: nowrap;
    }

    h1 {
      margin: 0 0 5px;
      font: 165% verdana, arial, helvetica
    }

    h2 {
      margin-top: 1em;
      margin-bottom: 0.5em;
      font: bold 125% verdana, arial, helvetica
    }

    h3 {
      margin-bottom: 0.5em;
      font: bold 115% verdana, arial, helvetica
    }

    img {
      border-width: 0;
    }

    div {
      margin-bottom: 20px;
    }

    div.details ul li {
      list-style: none;
    }
  </style>
</head>
<body>
  <h1>JMeter Summary</h1>
<#if !self?keys?has_content>
  <p>${self}</p>
<#else>
  <#list self("tests") as test>
    <a href="${test?url?url}.html">${test}</a><br/>
  </#list>
</#if>
</body>
</html>