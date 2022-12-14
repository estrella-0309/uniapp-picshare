const state={	
		id:"",
		appKey:"",
		username:"",
		password:null,
		sex:null,
		introduce:"",
		avatar:"",
		createTime:"",
		lastUpdateTime:""
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