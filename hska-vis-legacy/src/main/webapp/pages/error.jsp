<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
		<title><s:text name="details.head" /></title>
		<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" type="text/css" href="bootstrap/css/custom.css" />
		<script type="text/javascript" src="jquery/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	</head>
	<body>
		<nav class="navbar navbar-default" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand" href="http://www.iwi.hs-karlsruhe.de">Informatik</a>
				</div>
			</div>
		</nav>
		<div class="container">
			<div class="row">Es ist ein Fehler aufgetreten:</div>

			<div class="row">
				<h2>
					<s:actionerror />
				</h2>
			</div>

			<a href="./LoginAction.action">[<s:text name="link.back" />]</a>
		</div>
	</body>
</html>
