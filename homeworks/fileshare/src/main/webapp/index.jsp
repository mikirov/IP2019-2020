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

    <label for="innerFolderName_0">Create inner folder name</label>
    <input type="text" name="innerFolderName" id="innerFolderName_0">
    <button onclick="createFolder(0)">Create folder</button><br>

  <form method="POST" enctype="multipart/form-data" action="<c:url value="/file/upload"/>">
        <input type="file" name="file">
        <button type="submit">Upload file</button>
        <input type="hidden" name="parentId" value="0">
  </form>
    <c:if test="${error}">
        <p>${error}</p>
    </c:if>

    <ul>
     <c:forEach items="${files}" var="file">
         <li ${file}>
             <label for="fileId_${file.id}">File id:</label>
             <input id="fileId_${file.id}" disabled value="${file.id}"><br>

             <label for="fileName_${file.id}"></label>
             <input id="fileName_${file.id}" disabled value="${file.name}"><br>

             <label for="fileType_${file.id}">Is folder:</label>
             <input id="fileType_${file.id}" disabled value="${file.folder}"><br>

             <label for="fileAuthor_${file.id}">File author</label>
             <input id="fileAuthor_${file.id}" disabled value="${file.author.id}"><br>

             <label for="fileParent_${file.id}">File parent Id:</label>
             <input id="fileParent_${file.id}" disabled value="${file.parent.id}"><br>


             <label for="folderParent_${file.id}">Update folder parent</label>
             <input type="text" name="folderParent" id="folderParent_${file.id}">
             <button onclick="updateFolderParent(${file.id})">Update folder parent</button><br>


             <button onclick="createLink(${file.id})">Create Link</button><br>
             <label for="createLink_${file.id}">Unique link</label>
             <input id="createLink_${file.id}" disabled>
             <c:if test="${file.folder == true}">

                 <a href="http://localhost:8080/?parentId=${file.id}">Show folder</a><br>
                 <label for="innerFolderName_${file.id}">Create inner folder name</label>
                 <input type="text" name="innerFolderName" id="innerFolderName_${file.id}">
                <button onclick="createFolder(${file.id})">Create folder</button><br>

                 <label for="folderName_${file.id}">Update folder name</label>
                 <input type="text" name="folderName" id="folderName_${file.id}">
                 <button onclick="updateFolderName(${file.id})">Update folder name</button><br>

                 <form method="POST" enctype="multipart/form-data" action="<c:url value="/file/upload"/>">
                     <input type="file" name="file">
                     <button type="submit">Upload file</button>
                     <input type="hidden" name="parentId" value="${file.id}">
                 </form><br>
                 <button onclick="deleteFolder(${file.id})">Delete folder</button><br>

             </c:if>
             <c:if test="${file.folder == false}">
                 <a href="/file/download?fileId=${file.id}">Download file</a><br>
                 <button onclick="deleteFile(${file.id})">Delete file</button>
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
