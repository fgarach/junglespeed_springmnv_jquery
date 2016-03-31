<%-- 
    Document   : lister
    Created on : 29 mars 2016, 08:03:25
    Author     : tom
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Liste des parties</h1>

<c:forEach items="${parties}" var="partie">
    ${partie.id}  <input type="button" value="Rejoindre" onclick="rejoindrePartie(${partie.id})"/>
    <c:forEach items="${partie.utilisateurs}" var="utilisateur">
        ${utilisateur.nom}
    </c:forEach>
    <br>
</c:forEach>
