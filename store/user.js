const state={
		
		id:"1576783356459356160",
		appKey:"24d8ed2ab0444b048cbd5fcdde289109",
		username:"jtw",
		password:null,
		sex:1,
		introduce:"1",
		avatar:"https://guet-lab.oss-cn-hangzhou.aliyuncs.com/api/2022/12/13/a7a4d876-2be6-4366-8abe-486c71dd3be0.png",
		createTime:"1664769422055",
		lastUpdateTime:"1670919950081"
	};
	
	const mutations={
		GETUSERLIST(state,data) {
			state.appKey=data.appKey,
			state.avatar=data.avatar,
			state.createTime=data.createTime;
			state.id=data.id,
			state.introduce=data.introduce,
			state.lastUpdateTime=data.lastUpdateTime,
			state.password=data.password,
			state.sex=data.sex,
			state.username=data.username
		}
	}
	const actions={
		getUserList({commit},data){			
			commit("GETUSERLIST",data)
		}
	}
	const getters={
		
	}
	export default {
		state,
		actions,
		mutations,
		getters
	}