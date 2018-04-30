<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <link href="<c:url value="css/app.css" />" rel="stylesheet"
          type="text/css">
    <title>Articles</title>
</head>
<body class="security-app">

<div class="lc-block" align="center">
    <form:form method="post" modelAttribute="article">
        <table border="0">
            <tr>
                <td colspan="2" align="center"><h2>Create new article</h2></td>
            </tr>
            <tr>
                <td>Votes #:</td>
                <td><form:input path="votes" value="0" readonly="true"/></td>
            </tr>
            <tr>
                <td>By user:</td>
                <td><form:input path="username" value="${pageContext.request.remoteUser}" readonly="true"/></td>
            </tr>
            <tr>
                <td>Headline:</td>
                <td><form:input path="headline" id="headline"/> </td>
                <td><div id="headlineErrorMsg"></div></td>
            </tr>
            <tr>
                <td>Link:</td>
                <td><form:input path="url" id="url"/></td>
                <td><div id="urlErrorMsg"></div></td>
            </tr>
            <tr>
                <td>Author:</td>
                <td><form:input path="author" id="author"/></td>
                <td><div id="authorErrorMsg"></div></td>
            </tr>
            <tr>
                <td>
                    <input type="submit" formaction="/articles?cancel" value="Cancel"/>
                </td>
                <td colspan="2" align="center">
                    <input type="submit" formaction="/articles?add" value="Publish"/>
                </td>
            </tr>
        </table>
    </form:form>
</div>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript">
    $('form').submit(function (e) {

        if($(document.activeElement).val()=="Cancel"){
            return true;
        }

        var headline = $.trim($('#headline').val());
        var author = $.trim($('#author').val());
        var url = $.trim($('#url').val());
        var exists = urlExists(url);

        if (headline  === '') {
            $('#headlineErrorMsg').text("Molimo unesite naziv članka.");
        } else {
            $('#headlineErrorMsg').text("");
        }
        if (author  === '') {
            $('#authorErrorMsg').text("Molimo unesite ime autora.");
        } else {
            $('#authorErrorMsg').text("");
        }
        if (url  === '' || !exists) {
            $('#urlErrorMsg').text("Uneseni link nije ispravan.");
        } else {
            $('#urlErrorMsg').text("");
        }

        if(headline  === '' || author  === '' || url  === '' || !exists) {
            e.preventDefault();
            return false;
        }
    });

    function urlExists(url) {
        return true
    }
</script>

</body>
</html>
