<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Create an account</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
  <div class="container">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>
    </c:if>
  </div>
    <ul>
     <c:forEach items="${files}" var="file">
         <li ${file}>
             <c:if test="${file.folder == true}">
                 <label for="fileId_${file.id}">File id:</label>
                 <input id="fileId_${file.id}" disabled value="${file.id}">
                 <button onclick="showFolder(${file.id})">Show folder</button>
<%--                 <input type="hidden" id="${file.id}">--%>
                 <label for="innerFolderName_${file.id}">Create inner folder name</label>
                 <input type="text" name="innerFolderName" id="innerFolderName_${file.id}">
                <button onclick="createFolder(${file.id})">Create folder</button>

                 <label for="folderName_${file.id}">Update folder name</label>
                 <input type="text" name="folderName" id="folderName_${file.id}">
                 <button onclick="updateFolderName(${file.id})">Update folder name</button>

                 <label for="folderParent_${file.id}">Update folder parent</label>
                 <input type="text" name="folderParent" id="folderParent_${file.id}">
                 <button onclick="updateFolderParent(${file.id})">Update folder parent</button>

                 <button onclick="deleteFolder(${file.id})">Delete folder</button>
                 <form method="POST" enctype="multipart/form-data" action="/file/upload">
                     <input type="file" name="file"> <br/><br/>
                     <button type="submit">Upload file</button>
                 </form>
                 <button onclick="createLink(${file.id})"></button>

             </c:if>
             <c:if test="${file.folder == false}">
                 <a href="/file/download/${file.name}">download file</a>
             </c:if>

         </li>
         
     </c:forEach>

  </ul>

  <label for="linkDelete">Delete link:</label>
  <input id="linkDelete" type="text">
  <button onclick="deleteLink()">Delete</button>

  <script src="${contextPath}/resources/js/index.js"></script>
  <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
