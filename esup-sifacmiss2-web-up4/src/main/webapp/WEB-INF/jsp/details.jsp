<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:forEach items="${ details }" var="detail">
<tr data-mission="${ mission }" class="details ${ detail.paid ? 'paid' : '' }">
  <td>${ detail.description }</td>
  <td>
    <fmt:formatNumber value="${ detail.montant }" type="currency" currencyCode="EUR"/>
  </td>
  <td colspan="3"></td>
</tr>
</c:forEach>
