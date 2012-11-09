<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:forEach items="${ details }" var="detail">
<tr data-mission="${ mission }" class="details ${ detail.paid ? 'paid' : '' }">
  <td>${ detail.description }</td>
  <td>
    <fmt:formatNumber value="${ detail.montant }" type="currency" currencyCode="EUR"/>
  </td>
  <td colspan="3"></td>
</tr>
</c:forEach>
<c:if test="${ empty details }">
  <tr data-mission="${ mission }" class="details empty">
    <td colspan="5">
      <spring:message code="details.empty"/>
    </td>
  </tr>
</c:if>
