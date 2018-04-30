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
    <a href="/admin">Home</a><br>
    <br>
    Search: <input type="text" id="searchBox" value="${search}"><br>
    <form method="get" action="/admin">
        <input type="submit" value="Delete selected articles" id="delete_btn"/>
    </form>
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
                            <input type="checkbox" class="delete_chk" data-id=${article.id}>
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
                window.location.href = "/admin?search=" + query;
            } else if (e.which == 13 && query.length == 0) {
                window.location.href = "/admin";
            }
        });

        $("#sizeSelect").on('change', function () {
            var selectedSize = $("#sizeSelect").val();
            window.location.href = "/articles?&size=" + selectedSize + "&search=" + $("#searchBox").val() + "&sort=${sort}";
        });

        $("#delete_btn").click(function () {
            var allValues = [];
            $(".delete_chk:checked").each(function() {
                allValues.push($(this).attr('data-id'));
            });
            if(allValues.length <=0) {
                alert("Please select row.");
                return;
            }

            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            alert(JSON.stringify(allValues));

            $.ajax({
                type: "POST",
                url: "/delete",
                traditional: true,
                data: {
                    articles : allValues.toString()
                },
                dataType : "json",
                async : true,
                beforeSend : function(xhr) {
                    xhr.setRequestHeader("Accept", "application/json");
                    xhr.setRequestHeader("Content-Type", "application/json");
                    xhr.setRequestHeader(header, token);
                },
                success: function() {
                    window.location.href = "/admin?deleted=" + allValues.length;
                },
                error : function(e) {
                    alert('Error: ' + e);
                }
            });

            alert("After ajax");
        })
    });
</script>
</body>
</html>