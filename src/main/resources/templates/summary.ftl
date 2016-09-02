<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="css/owfont-regular.min.css" rel="stylesheet" type="text/css">
    <link href="css/style.css" rel="stylesheet" type="text/css">
    <title>Weather Summary</title>
</head>
<body>
<div id="twitter"><a href="https://twitter.com/spring1platform">@spring1platform</a></div>
<h1>Weather Summary</h1>
<ul>
    ${summary[0]?county}
<#list summary as item>
    <li>
        ${item?values}
        <#--<h2>${summary.get(0).city}, ${summary[0].country}</h2>-->
        <#--<div>-->
            <#--<i class="owf owf-{{code}} owf-4x" style="opacity: 0.4"></i>-->
            <#--<div class="temp">${summary[0].fahrenheitTemperature}<sup>°F</sup> / ${summary[0].celsiusTemperature}<sup>°C</sup></div>-->
        <#--</div>-->
        <#--<div style="clear: both;"></div>-->
    </li>
</#list>
</ul>

</body>
</html>
