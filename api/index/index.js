import api from '../api.js'
const header={
			  "Accept": "application/json, text/plain, */*",
			  "Content-Type": "application/x-www-form-urlencoded",
			  "appId": "24d8ed2ab0444b048cbd5fcdde289109",
			  "appSecret": "300002f6abcaf485d4cb19de0695a0b049dc0"
		};
const Login=async(username,password)=>{
	let data =await api({
		url:'user/login',
		method:'POST',
		header:header,
		data:{
			username:username,password:password
			
		},
	})
	return data
}
export{
	Login
}