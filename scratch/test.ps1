$response = Invoke-WebRequest -Uri 'http://localhost:8080/session/start' -Method POST -Body @{email='employee@demo.local';password='123456'} -SessionVariable mySession -MaximumRedirection 0 -ErrorAction SilentlyContinue
Write-Output "Status: $($response.StatusCode)"
$mySession.Cookies.GetCookies('http://localhost:8080')
