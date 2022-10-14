const header={ Accept: "application/json, text/plain, */*",
			  "Content-Type": "application/json",
			  appId: "24d8ed2ab0444b048cbd5fcdde289109",
			  appSecret: "300002f6abcaf485d4cb19de0695a0b049dc0",
			  // appId: "d39fc189485c43d9a4b37463b238ac84",
			  // appSecret: "06219a004b5ecf6c84f89ba5f9d5c81a037f6",
			  };
const api=(Option)=>{
	let baseURL='http://47.107.52.7:88/member'
	return new Promise((resolve,reject)=>{
		uni.showLoading({
			title:'加载中'
		})
		uni.request({
			url:baseURL+Option.url,
			method:Option.method||'GET',
			header:Option.header||header,
			data:Option.data||{},
			success:res=>{		
				uni.hideLoading()
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