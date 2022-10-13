const state={
		
		id:"1576783356459356160",
		appKey:"24d8ed2ab0444b048cbd5fcdde289109",
		username:"jtw",
		password:null,
		sex:null,
		introduce:null,
		avatar:null,
		createTime:"1664769422055",
		lastUpdateTime:"1664769422055"
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
			console.log(data,88);
			
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