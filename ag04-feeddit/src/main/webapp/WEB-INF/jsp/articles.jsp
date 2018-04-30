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
    <title>Articles</title>
</head>
<body class="security-app">
<div class="lc-block">
    Welcome <a href="/admin">${pageContext.request.remoteUser}</a><br>
    <form method="get" action="/create_article">
        <input type="submit" value="Create new article"/>
    </form>
    <br>
    Search: <input type="text" id="searchBox" value="${search}"><br>
    <c:forEach var="s" items="${sortList}">
        <a href="/articles?size=${size}&sort=${s.key}&search=${search}">${s.value}</a>
    </c:forEach><br>

    <c:choose>
        <c:when test="${not empty articleList}">
            <c:forEach var="article" items="${articleList}">
                <table>
                    <tr>
                        <td rowspan="2">${article.voteCount}</td>
                        <td><a href="${article.url}">${article.headline}</a></td>
                    </tr>
                    <tr>
                        <td>${article.author}</td>

                        <td>
                            <form:form method="POST" action="/articles?size=${size}&page=${page}&sort=${sort}&vote"
                                       modelAttribute="vote">
                                <form:hidden path="articleId" value="${article.id}"/>
                                <form:hidden path="username" value="${pageContext.request.remoteUser}"/>
                                <form:hidden path="result" value="true"/>
                                <input type="submit" value="Up"/>
                            </form:form>
                        </td>
                        <td>
                            <form:form method="POST" action="/articles?size=${size}&page=${page}&sort=${sort}&vote"
                                       modelAttribute="vote">
                                <form:hidden path="articleId" value="${article.id}"/>
                                <form:hidden path="username" value="${pageContext.request.remoteUser}"/>
                                <form:hidden path="result" value="false"/>
                                <input type="submit" value="Down"/>
                            </form:form>
                        </td>
                    </tr>
                </table>
            </c:forEach>
        </c:when>
        <c:otherwise>
            Trenutno ne postoji niti jedan članak.
        </c:otherwise>
    </c:choose>

    <c:if test="${page>0}">
        <form method="get" action="/articles">
            <input type="hidden" name="page" value="${page-1}"/>
            <input type="hidden" name="sort" value="${sort}"/>
            <input type="hidden" name="search" value="${search}"/>
            <input type="submit" value="Prev"/>
        </form>
    </c:if>

    ${page+1}/${numOfPages}

    <c:if test="${page<numOfPages-1}">
        <form method="get" action="/articles">
            <input type="hidden" name="page" value="${page+1}"/>
            <input type="hidden" name="sort" value="${sort}"/>
            <input type="hidden" name="search" value="${search}"/>
            <input type="submit" value="Next"/>
        </form>
    </c:if>
    <br>
    <form:select id="sizeSelect" path="size">
        <c:forEach items="${pageSizes}" var="s">
            <c:choose>
                <c:when test="${s.value == pageSizes.get(size)}">
                    <form:option label="${s.value}" value="${s.key}" selected="selected"/>
                </c:when>
                <c:otherwise>
                    <form:option label="${s.value}" value="${s.key}"/>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </form:select>

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
                window.location.href = "/articles?size=${size}&search=" + query;
            } else if (e.which == 13 && query.length == 0) {
                window.location.href = "/articles";
            }
        });
        $("#sizeSelect").on('change', function () {
            var selectedSize = $("#sizeSelect").val();
            window.location.href = "/articles?&size=" + selectedSize + "&search=" + $("#searchBox").val() + "&sort=${sort}";
        });
    });
</script>
</body>
</html>
