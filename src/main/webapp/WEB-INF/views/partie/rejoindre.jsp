<%-- 
    Document   : lister
    Created on : 29 mars 2016, 08:03:25
    Author     : tom
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


        <form:form modelAttribute="joueur" action="rejoindre" method="post">
            <form:hidden path="partie.id"/>
            Nom : <form:input path="nom"/>
            <input type="button" onclick="rejoindrePost();" value="Rejoindre"/>
        </form:form>
