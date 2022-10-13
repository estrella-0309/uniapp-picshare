
const api=(Option)=>{
	let baseURL='http://47.107.52.7:88/member/photo/'
	return new Promise((res,reject)=>{
		uni.showLoading({
			title:'加载中'
		})
		uni.request({
			url:baseURL+Option.url,
			method:Option.method||'GET',
			data:Option.data||{},
			success:res=>{
				if(res.data.success!=true){
					return uni.showToast({
						title:'获取数据失败',
						icon:'error'
					})
				}
				uni.hideLoading()
				resolve(res)
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