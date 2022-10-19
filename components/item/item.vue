<template>
	
	<view class="box">
		<view v-if="!list.length>0">
			
			<u-empty text="没有内容" mode="list" style="width:750rpx;"></u-empty>
		</view>
		<view class="list" v-for="(item,index) in list" :key="index">
		
			<view class="list-img">
				<u-image :src="item.imageUrlList[0]" width="100%" ></u-image>
			</view>
			<view class="list-con">
					<view class="list-foot">			
						<view class="title">{{item.title}}</view>
						<view class="content">{{item.content}}</view>
						<div class="icon" v-if="type.txt=='My'">
								<u-icon   name="trash"  size="25"  @click="Delete(item)"></u-icon>		
						</div>
						<div class="icon" v-else>
							<view @tap.stop>
									<u-icon   @tap="ClickLike(item,userid)"   :name="item.hasLike?'thumb-up-fill':'thumb-up'"  size="25" class="iconimg-2" ></u-icon>
							</view>
								<view  @tap.stop>
									<u-icon   @tap="ClickCollect(item,userid)" :name="item.hasCollect?'star-fill':'star'"  size="25" class="iconimg-2" ></u-icon >	
								</view>
								
						</div>
						</view>
					</view>
				
			</view>
			
		</view>
		</view>
	</view>
		
</template>

<script>
	import {mapState} from "vuex"
	import {GetMy,Getlike, GetCollect,DeleteShare} from "@/api/index/index.js"
	import Checked from '@/mixins/index.js'
	export default{			
		mixins: [Checked],
		props:["type"],
		data() {
			 return{
				 list:[],
				 page:1,

			 }
		},
		mounted() {
			this.getData()
		},
		methods:{
			async Delete(item){
				let result=await DeleteShare(item.id,this.userid)
				if(result.code==200){
					this.list=this.list.filter(i =>{
						return i.id!=item.id
					})
				}
			},
			async getData(){
				console.log(this.page,"page")
				let result;
				switch(this.type.txt){
		
					case 'My':
					result=await GetMy(this.page,this.userid);
					console.log(result)
					if(result.code==200){
						
						this.list=[...this.list,...result.data.records]
						console.log(this.list)
					}
					break;
					case 'Like':
					result=await Getlike(this.page,this.userid);
					console.log(result)
					if(result.code==200){
						this.list=[...this.list,...result.data.records]
						console.log(this.list)
					}
					break;
					case 'Collect':
					result=await GetCollect(this.page,this.userid);
					console.log(result)
					if(result.code==200){
						this.list=[...this.list,...result.data.records]
						console.log(this.list)
					}
					break;
				}
				this.page++;
			}
		},
		computed: {
			...mapState({
				userid:state=>state.user.id,
				username:state=>state.user.username,
			}),
		},
	}
</script>

	 
 <style lang="scss" scoped>
	 .box{
		 display: flex;
		  flex-wrap: wrap;
		 .list{
			  background-color:#DFE0EF ;
		 		 // background-color: blue;
		 		 margin-right:13rpx;
				 margin-bottom: 20rpx;
				 // border:1px solid black;
		 		 width:49%;
				 // background-color: #fff;
				 border-radius:10px ;
				 // height: 600rpx;
		 }
		 .list:nth-child(even){
			 margin-right:0;
		 }
		 .title{
			 font-size:23px;
		 }
		 .content{
				font-size:16px;
				overflow: hidden;
			    text-overflow: ellipsis;
			    white-space: nowrap;
		 }
		 .icon{
			 display: flex;
			 margin-top: 10rpx;
			 width:100%;
			 justify-content: flex-end;
			 .like{		 
				flex:1;
			 }
			 .collect{
				flex:1;
			 }
		 }
	 }

</style>