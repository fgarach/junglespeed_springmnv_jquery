<%-- 
    Document   : lister
    Created on : 29 mars 2016, 08:03:25
    Author     : tom
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


Numero de la partie : ${partie.id}
<br>
<br>
<br>
<%--<c:forEach items="${joueurs}" var="utilisateur">
    ${utilisateur.nom}    
</c:forEach>--%>
${joueur1.nom}
<br>

${score1}

${carteRetournee1.couleur}

<br>
<br>
<br>
<c:if test="${!empty joueur2}">
${joueur2.nom}
</c:if>
<br>
${score2}
<c:if test="${!empty carteRetournee2}">
${carteRetournee2.couleur}
</c:if>
<br>
