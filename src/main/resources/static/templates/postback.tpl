<html>
	<head>postback</head>
	<body>
		<form name="theform" enctype="multipart/form-data" method="post">
			<input type="text" name="text" value="some text" />
			<br />
			<input type="file" name="somefile" />
			<br />
			<input type="submit" />
			<br /><br />
			<#if image??>
				<#if mime??>
					<img src="data:${mime};base64, ${image}" />
				</#if>
			</#if>
		</form>
	</body>
</html>