<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Create an account</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <script>

        $( document ).ready(function() {
            console.log( "ready!" );

            function getPath(element){
                if(element.id === "root"){
                    return "/"
                }
                return getPath(element.getParent()) + element.id + "/";
            }

            $("#createFolderBtn").click(function () {
                console.log("$(\"#createFolderBtn\").click")
                $.ajax({
                    url: '127.0.0.1:8080/create-folder',
                    type: 'POST',
                    data: {name: $("#createFolderName").text , path: getPath(this)},
                    success: function(result){
                        location.reload();
                    }
                });
            });

            $("#updateFolderBtn").click(function () {
                console.log("$(\"#updateFolderBtn\").click")
                $.ajax({
                    url: '127.0.0.1:8080/update-folder',
                    type: 'PUT',
                    data: {name: $("#updateFolderName").text , path: getPath(this)},
                    success: function(result){
                        location.reload();
                    }
                });
            });


            $("#deleteFolderBtn").click(function () {
                console.log("$(\"#deleteFolderBtn\").click")
                $.ajax({
                    url: '127.0.0.1:8080/delete-folder',
                    type: 'DELETE',
                    data: {path: getPath(this)},
                    success: function(result){
                        location.reload();
                    }
                });
            });
        });

    </script>
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
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    <ul>
        <li id="root">Root:</li>

        <label for="createFolderName">Folder Name:</label>
        <input type="text" name="createFolderName" id="createFolderName">
        <button id="createFolderBtn">Create folder</button>
        <br>
        <label for="updateFolderName">Folder Name:</label>
        <input type="text" name="updateFolderName" id="updateFolderName">
        <button name="updateFolderBtn" id="updateFolderBtn">Update Folder</button>
        <br>
        <button id="deleteFolderBtn" name="deleteFolder">Delete folder</button>

  </ul>

  <h4>Upload Single File:</h4>
  <form method="POST" enctype="multipart/form-data" action="<c:url value="/upload-file"/>">
      <input type="file" name="file"> <br/><br/>
      <button type="submit">Submit</button>
  </form>

  <h2>All Uploaded Files:</h2>
  <ul>
      <c:forEach items="${files}" var="file">
          <li ${file}>
              <a href="${file}" target="_blank" >${file}</a>
          </li>
      </c:forEach>
  </ul>

</body>
</html>
