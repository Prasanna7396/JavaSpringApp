<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<body>
<h1 id="myMsg">${msg}</h1>
<h2 id="myEnv">${env}</h2>
<h2 id="myTime">Today is <fmt:formatDate value="${today}" pattern="yyy-MM-dd" /></h2>
</body>
</html>
