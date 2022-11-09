const header={ Accept: "application/json, text/plain, */*",
			  "Content-Type": "application/json",
			  appId: "24d8ed2ab0444b048cbd5fcdde289109",
			  appSecret: "300002f6abcaf485d4cb19de0695a0b049dc0",
			  };
const api=(Option)=>{
	let baseURL='http://47.107.52.7:88/member'

	return new Promise((resolve,reject)=>{
		getApp().globalData.ApiCount++;
		uni.showLoading({
			title:'加载中'
		})
		if(Option.hasOwnProperty('Content-Type')){
			header['Content-Type']=Option["Content-Type"]
		}
		uni.request({
			url:baseURL+Option.url,
			method:Option.method||'GET',
			header:header,
			data:Option.data||{},
			success:res=>{
				getApp().globalData.ApiCount--;
				setTimeout(()=>{
					if(getApp().globalData.ApiCount==0){
						uni.hideLoading()
					}
				},500)
				
				resolve(res.data)
			},
			fail:err=>{
				uni.showToast({
					title:'请求接口失败',
					icon:'error'
				})
				reject(err)
			}
		})
	})
	
}
export default api