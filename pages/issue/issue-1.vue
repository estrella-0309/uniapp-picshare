<template>
	<view class="box">
		<view class="msg">
			<view class="title">
				标题：
				<el-input
				  placeholder="请输入内容"
				  v-model="title"
				  clearable 
				  style="width: 80%;">
				</el-input>
			</view>
			<view class="content">
				内容：
				<el-input
				  placeholder="请输入内容"
				  v-model="content"
				  clearable
				   style="width: 80%;">
				</el-input>
			</view>
		</view>
		<view class="images">
			<view class="images-title">
				上传照片（不能超过9张）
			</view>
			<el-upload
			class="upload-demo"
			  ref="upload"
			  action="http"
			  :file-list="fileList"
			  :auto-upload="false"	
				:http-request="upLoadFile"
				list-type="picture-card"
				multiple
				 >
			    <i slot="default" class="el-icon-plus"></i>
			    <div slot="file" slot-scope="{file}">
			      <img
			        class="el-upload-list__item-thumbnail"
			        :src="file.url" alt=""
			      >
				  <!-- {{file}} -->
			      <span class="el-upload-list__item-actions">
			        <span
			          v-if="!disabled"
			          class="el-upload-list__item-delete"
			          @click="handleRemove(file)"
			        >
			          <i class="el-icon-delete"></i>
			        </span>
			      </span>
			    </div>
				<div class="summit" @click="submitUpload">
					<u-button type="primary" ripple="true" ripple-bg-color="red"  >提交</u-button>
				</div>
			</el-upload>
		</view>
		
	</view>
</template>

<script>
	import{Upload} from '@/api/index/index.js'
	export default {
		data() {
			return {
				disabled: false,
				title:'',
				content:'',
				 fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]
			}
		},
		methods: {
			submitUpload() {
				this.$refs.upload.submit();
			},
			handleRemove(file, fileList) {
				console.log(file, fileList);
			},
			handlePreview(file) {
				console.log(file);
			},
			async upLoadFile(data){
				console.log(data.file,"in")
				const file=data.file;
				const form=new FormData();
				console.log(file,"fileList");
				form.append("fileList",file);
				console.log(form,"form")
				let result=await Upload(data.file);
				console.log(result)
				
			}
		}
	}
</script>

<style>
	
</style>
<style lang="scss" scoped>
/deep/ .el-upload--picture-card{
	width: 200rpx;
	height: 200rpx;
	i{
		position: relative;
		top: -40rpx;
	}
}
/deep/ .el-upload-list__item  {
	  width: 200rpx;
	  height: 200rpx;
	  margin-right: 31rpx;
	}
	.box{
		.summit{
			width: 750rpx;
			position: fixed;
			bottom: 100rpx;
			left: 0;
		}
		.msg {
			margin:50rpx 20rpx;
			.title{
				margin-bottom: 50rpx;
			}
		}
		.images{
			.images-title{
				margin-bottom: 20rpx;
				color: #ccc;
			}
			img{
				
				width: 200rpx;
				height: 200rpx;
			}
			margin: 50rpx 20rpx;
		}
	}
</style>
