<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="/static/css/owfont-regular.min.css" rel="stylesheet" type="text/css">
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <title>Weather Summary</title>
</head>
<body>
<div id="twitter"><a href="https://twitter.com/spring1platform">@spring1platform</a></div>
<h1>Weather Summary</h1>
<ul>
<#list summary as item>
    <li>
        <h2>${item.city}, ${item.country}</h2>
        <div>
            <i class="owf owf-${item.code} owf-4x" style="opacity: 0.4"></i>
            <div class="temp">${item.fahrenheitTemperature}<sup>°F</sup> / ${item.celsiusTemperature}<sup>°C</sup></div>
        </div>
        <div style="clear: both;"></div>
    </li>
</#list>
</ul>

</body>
</html>
