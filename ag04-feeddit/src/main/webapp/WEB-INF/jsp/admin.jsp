<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link href="<c:url value="css/app.css" />" rel="stylesheet"
          type="text/css">
    <title>Administration</title>
</head>
<body class="security-app">
<div class="lc-block">
    <a href="/articles">Home</a><br>
    <br>
    <form method="get" action="/create_article">
        <input type="submit" value="Create new article"/>
    </form>
    <br>
    Search: <input type="text" id="searchBox" value="${search}">
    <br>
    Sort: <form:select id="sortSelect" path="size">
    <c:forEach items="${sortList}" var="s">
        <form:option label="${s.value}" class="sortOption" value="${s.key}"/>
    </c:forEach>
</form:select>
    <br><br>

    <c:choose>
        <c:when test="${not empty articleList}">
            <form:form method="POST" action="/admin?page=${page}&sort=${sort}&delete"
                       modelAttribute="articleIds">
                <input type="submit" value="Delete selected articles"/>
                <c:forEach var="article" items="${articleList}">
                    <table>
                        <tr>
                            <td rowspan="2">${article.voteCount}</td>
                            <td><a href="${article.url}">${article.headline}</a></td>
                        </tr>
                        <tr>
                            <td>${article.author}</td>

                            <td>
                                <form:checkbox path="articleIds" value="${article.id}"/>
                            </td>
                        </tr>
                    </table>
                </c:forEach>
            </form:form>
        </c:when>
        <c:otherwise>
            Trenutno ne postoji niti jedan članak.
        </c:otherwise>
    </c:choose>
    <c:if test="${page>0}">
        <form method="get" action="/admin">
            <input type="hidden" name="page" value="${page-1}"/>
            <input type="hidden" name="sort" value="${sort}"/>
            <input type="hidden" name="search" value="${search}"/>
            <input type="submit" value="Prev"/>
        </form>
    </c:if>

    ${page+1}/${numOfPages}

    <c:if test="${page<numOfPages-1}">
        <form method="get" action="/admin">
            <input type="hidden" name="page" value="${page+1}"/>
            <input type="hidden" name="sort" value="${sort}"/>
            <input type="hidden" name="search" value="${search}"/>
            <input type="submit" value="Next"/>
        </form>
    </c:if>
    <br>

    <form action="/logout" method="post">
        <input type="submit" class="button red big" value="Sign Out"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#searchBox").keypress(function (e) {
            var query = $.trim($("#searchBox").val());
            if (e.which == 13 && query.length >= 3) {
                window.location.href = "/admin?search=" + query;
            } else if (e.which == 13 && query.length == 0) {
                window.location.href = "/admin";
            }
        });

        $('#sortSelect').on('click', function (ev) {
            if (ev.offsetY < 0) {
                var selectedSort = $("#sortSelect option:selected").val();
                window.location.href = "/admin?search=${search}&sort=" + selectedSort;
            } else {
                //dropdown is shown
            }
        });
    });
</script>
</body>
</html>