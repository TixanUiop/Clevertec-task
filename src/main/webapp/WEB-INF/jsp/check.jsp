<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="total" value="0" scope="page"/>
<jsp:useBean id="now" class="java.util.Date" scope="page"/>


<c:if test="${not empty productMapResult}">
    <h2>Результаты</h2>
    <p>Магазин: Магазин X</p>
    <p>Телефон: +1-234-567-890</p>

    <p>Дата: <fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/></p>

    <table border="1">
        <thead>
        <tr>
            <th>Количество</th>
            <th>Продукт</th>
            <th>Цена</th>
            <th>Сумма</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="entry" items="${productMapResult}">

            <c:set var="quantity" value="${entry.key}" />
            <c:set var="product" value="${entry.value}" />
            <c:set var="useDiscount" value="${product.salePrice != null && quantity > 5 && cardPresent}" />

            <c:choose>
                <c:when test="${useDiscount}">
                    <c:set var="actualPrice" value="${product.salePrice}" />
                    <c:set var="labelledPrice" value="Скидка ${product.salePrice}" />
                </c:when>
                <c:otherwise>
                    <c:set var="actualPrice" value="${product.price}" />
                    <c:set var="labelledPrice" value="${product.price}" />
                </c:otherwise>
            </c:choose>

            <c:set var="lineTotal" value="${quantity * actualPrice}" />
            <c:set var="total" value="${total + lineTotal}" scope="page" />

            <tr>
                <td>${quantity}</td>
                <td>${product.description}</td>
                <td>${labelledPrice}</td>
                <td>${lineTotal}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div>
        <c:choose>
            <c:when test="${card}">
                <c:set var="finalTotal" value="${total * 0.9}" />
                <p><strong>Итого к оплате:</strong>
                    <fmt:formatNumber value="${finalTotal}" type="number" maxIntegerDigits="6" maxFractionDigits="2"/>
                    <span style="color: green;">(скидка по карте -10%)</span>
                </p>
            </c:when>
            <c:otherwise>
                <p>Итого к оплате: ${total}</p>
            </c:otherwise>
        </c:choose>
        <p>Карта: ${card ? 'предъявлена' : 'не предъявлена'}</p>
    </div>
</c:if>

<c:if test="${empty productMapResult}">
    <p style="color: red;">Нет товаров</p>
</c:if>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f8f9fa;
        color: #333;
        padding: 20px;
    }

    h2 {
        color: #0056b3;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 15px;
    }

    table, th, td {
        border: 1px solid #dee2e6;
    }

    th {
        background-color: #343a40;
        color: white;
        padding: 8px;
    }

    td {
        text-align: center;
        padding: 8px;
    }

    .summary {
        margin-top: 20px;
        font-size: 1.1em;
    }

    .card-present {
        color: green;
        font-weight: bold;
    }

    .card-absent {
        color: red;
        font-weight: bold;
    }

    .discount-label {
        color: #28a745;
        font-weight: bold;
    }
</style>