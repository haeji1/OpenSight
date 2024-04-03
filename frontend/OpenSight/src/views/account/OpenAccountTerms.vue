<script setup>
import { useRouter } from 'vue-router';
import { useAccountStore } from '@/stores/account';

const router = useRouter()


const AccountStore = useAccountStore();


const openAccount = () => {
  
  AccountStore.openAccount();
  router.push('/account/open/complete')
  //router.push('/account/open/terms');
}



</script>

<template>
<div class="container">
  <div class="header">
    <div class="haeder-back">
      <p class="title2">&lt;</p><p class="title2">비대면계좌 개설</p>
    </div>
    <p class="title1">{{AccountStore.selectedAccountType.accountName }}</p>
  </div>
  <div>
    <div>
      <table class="tbl-type-1 ui-set-tbl-type" cellspacing="0" summary="1. 수집 · 이용에 관한 사항, 수집 · 이용 목적 , 수집 · 이용할 항목, 보유 · 이용 기간, 동의를 거부할 권리 및 동의를 거부할 경우의  불이익 제공">
				<caption>필수사항에 대한 동의안내</caption>
				<colgroup>
					<col width="25%">
					<col width="75%">
				</colgroup>
				<thead>
					<tr>
						<th colspan="2" class="txt-l">1. 수집 · 이용에 관한 사항</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th scope="row">수집 · 이용 목적 </th>
						<td class="txt-l">
							<ul class="fleft">
								<li>(금융)거래관계의 설정 여부 판단</li>
								<li>금융사고 조사, 분쟁 해결, 민원 처리</li>
							</ul>
							<ul class="fleft">
								<li>(금융)거래관계의 설정 · 유지 · 이행 · 관리</li>
								<li>법령상 의무이행</li>
							</ul>
						</td>
					</tr>
					<tr>
						<th scope="row">수집 · 이용할 항목</th>
						<td class="txt-l">
							<span class="font-bold ml10">[필수정보]</span>
							<dl class="ml10 pv0">
								<dt class="font-normal">공통정보</dt>
								<dd>‣ 성명, <span class="txt-l font-16 font-bold">고유식별정보</span>, 주소, 연락처, 직업군, 국적</dd>
							</dl>
							<dl class="ml10 pv0">
								<dt class="font-normal">신용평가를 위한 정보(신용평가를 수반하지 않는 대출 제외)</dt>
								<dd>‣ 직업,직장명,근무부서,직위,입사일자,직무,연소득,주택소유여부,주거종류,주거상황</dd>
							</dl>
							<dl class="ml10 pv0">
								<dt class="font-normal">(금융)거래정보</dt>
								<dd>‣ 상품종류, 거래조건(이자율, 만기 등), 거래일시, 금액 등 거래 설정 · 내역 정보 및 (금융)거래의 설정 · 유지 · 이행 · 관리를 위한 <span class="ml10">상담을 통해 생성되는 정보<span></span></span></dd>
							</dl>
							<span class="ml10 font-normal">고객 ID, 접속일시, IP주소, 이용 전화번호 등 전자금융거래법에 따른 수집 정보 [전자금융거래에 한함]</span>
							<span class="ml10">※ 본 동의 이전에 발생한 개인정보도 포함됩니다.</span>
							
						</td>
					</tr>
					<tr>
						<th scope="row">보유 · 이용 기간</th>
						<td class="txt-l font-13"><span class="ml10 font-16 font-bold">위 개인(신용)정보는 (금융)거래 종료일* 로부터 5년까지 보유·이용됩니다. (금융)거래 종료일 후에는 금융사고 조사, 분쟁 해결, 민원처리, 법령상 의무이행 및 리스크 관리업무만을 위하여 보유·이용됩니다.<br>
						* (금융)거래 종료일이란 당행과 거래중인 모든 계약(여·수신, 내·외국환 및 제3자 담보제공 등) 및 서비스(대여금고, 보호예수, 외국환거래지정, 인터넷뱅킹 포함 전자금융거래 등)가 종료한 날을 뜻합니다.
						</span></td>
					</tr>
					<tr>
						<th scope="row">동의를 거부할 권리 및 동의를 거부할 경우의  불이익</th>
						<td class="txt-l font-13"><span class="ml10">위 개인(신용)정보 수집 · 이용에 관한 동의는 계약의 체결 및 이행을 위하여 필수적이므로, 위 사항에 동의하셔야만 (금융)거래관계의 설정 및 유지가 가능합니다.</span></td>
					</tr>
				</tbody>
			</table>
      <table class="tbl-type-1 mt10 ui-set-tbl-type" cellspacing="0" summary="2. 제공에 관한 사항, 제공받는 자, 제공받는 자의 이용 목적, 제공할개인(신용)정보의항목, 제공 및 변경에관한 세부사항, 제공받은 자의개인(신용)정보보유 · 이용 기간, 동의를 거부할 권리 및동의를 거부할 경우의  불이익 제공">
				<colgroup>
					<col width="25%">
					<col width="75%">
				</colgroup>
				<thead>
					<tr>
						<th colspan="2" class="txt-l">2. 제공에 관한 사항</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th scope="row">제공받는 자</th>
						<td class="txt-l">
							<dl class="ml10 pv0">
								<dt><span class="txt-l font-16 font-bold">신용정보집중기관 및 신용조회회사에 대한 제공</span></dt>
								<dd><span class="txt-l font-16 font-bold">신용정보집중기관 : [한국신용정보원]</span></dd>
								<dd><span class="txt-l font-16 font-bold">신용조회회사 : [코리아크레딧뷰로(주), NICE평가정보(주), 한국기업데이터(주)]</span></dd>
							</dl>
						</td>
					</tr>
					<tr>
						<th scope="row">제공받는 자의 이용 목적</th>
						<td class="txt-l">
							<dl class="ml10 pv0">
								<dt><span class="txt-l font-16 font-bold">신용정보집중기관 및 신용조회회사에 대한 제공</span></dt>
								<dd><span class="txt-l font-16 font-bold">본인의 신용을 판단하기 위한 자료로 활용하거나 공공기관에서 정책자료로 활용</span></dd>
							</dl>
						</td>
					</tr>
					<tr>
						<th scope="row">제공할 개인(신용)정보의 항목</th>
						<td class="txt-l">
							<dl class="ml10 pv0">
								<dt>신용정보집중기관 및 신용조회회사에게 제공되는 개인(신용)정보의 항목</dt>
								<dd>개인식별정보, 신용거래정보, 신용능력정보, 신용도판단정보, 신용평가를 위한 정보</dd>
							</dl>
						</td>
					</tr>
					<tr>
						<th scope="row">제공 및 변경에 관한 세부사항</th>
						<td class="txt-l">
							<ul>
								<li>[당 행]은 위 수탁업체에게 개인(신용)정보를 제공할 경우, 업무수행에 필요 최소한의 정보만을 제공하며, 위 제공대상 업체 현황·제공대상 개인(신용)정보 이용목적·제공대상 개인(신용)정보 항목의 세부적 내용은 [당 행]홈페이지*에서 확인하실 수 있습니다.</li>
							</ul>
						</td>
					</tr>
					<tr>
						<th scope="row">제공받은 자의 개인(신용)정보 보유 · 이용 기간</th>
						<td class="txt-l">
							<dl class="ml10 pv0">
								<dt><span class="txt-l font-16 font-bold">신용정보집중기관 및 신용조회회사에 대한 제공 </span></dt>
								<dd><span class="txt-l font-16 font-bold">등록사유 발생과 관련이 있는 거래가 존속하는 기간</span></dd>
							</dl>
							<ul>
								<li><span class="txt-l font-16 font-bold">제공된 목적 달성 후에는 위에 기재된 이용 목적과 관련된 금융사고 조사, 분쟁 해결, 민원처리, 법령상 의무이행을 위하여 필요한 범위 내에서만 보유·이용됩니다.</span></li>
							</ul>
						</td>
					</tr>
					<tr>
						<th>동의를 거부할 권리 및 동의를 거부할 경우의  불이익</th>
						<td class="txt-l font-13"><span class="ml10">위 개인(신용)정보의 제공에 관한 동의는 계약의 체결 및 이행을 위하여 필수적이므로, 위 사항에 동의하셔야만 (금융)거래관계의 설정 및 유지가 가능합니다.</span></td>
					</tr>
				</tbody>
			</table>
    </div>
    
  </div>
  <div class="button-group">
      <button class="button-cancel" @click="router.push('/main')">취소</button>
      <button class="button-confirm" @click="openAccount">계좌 개설</button>
    </div>
</div>
</template>

<style scoped>
.container {
  background-color: #ffffff;
}

.header {
  display: flex;
  flex-direction: column;
  margin-left: 20px;
}

.haeder-back {
  display: flex;
  flex-direction: row;
}

.title2 {
  margin-right: 15px;
}

.button-group {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
}

.button-cancel {
  margin-top: 30px;
  width: 160px;
  height: 45px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #e0e0e0;
  color: #000000;
  border-radius: 10px;
  border: none;
  margin-right: 10px;
}

.button-confirm {
  margin-top: 30px;
  width: 160px;
  height: 45px; 
  font-size: 16px;
  font-weight: 500;
  background-color: #1B3C62;
  color: #ffffff;
  border: none;
  border-radius: 10px;
  margin-left: 10px;
}

/* 테이블 전체에 대한 스타일 */
.tbl-type-1 {
  width: 100%;
  border-collapse: collapse;
  text-align: left;
  margin-top: 10px; /* 상단 여백을 조정하고 싶다면 이 값을 변경하세요. */
}

/* 캡션에 대한 스타일 */
.tbl-type-1 caption {
  text-align: center;
  font-weight: bold;
  padding: 10px; /* 캡션의 패딩을 조정하고 싶다면 이 값을 변경하세요. */
}

/* 테이블 헤더에 대한 스타일 */
.tbl-type-1 thead th {
  background-color: #f2f2f2; /* 헤더의 배경 색상을 조정하고 싶다면 이 값을 변경하세요. */
  color: #000; /* 텍스트 색상 */
  padding: 15px;
}

/* 테이블 바디에 대한 스타일 */
.tbl-type-1 tbody th,
.tbl-type-1 tbody td {
  border-top: 1px solid #eaeaea; /* 테두리 상단 색상 */
  padding: 15px; /* 셀 내부 패딩 */
}

/* 본문 글자에 대한 스타일 */
.tbl-type-1 .txt-l {
  text-align: left;
}

/* 리스트 내부에 여백 및 스타일을 적용합니다. */
.tbl-type-1 ul {
  padding-left: 20px; /* 리스트 앞쪽 여백 */
  list-style-type: none; /* 기본 리스트 스타일 없애기 */
}

/* 리스트 내부 li 태그에 스타일을 적용합니다. */
.tbl-type-1 li:before {
  content: "•"; /* 리스트 마커로 '•' 사용 */
  color: #000; /* 마커 색상 */
  font-weight: bold; /* 마커 굵기 */
  display: inline-block; 
  width: 1em; /* 마커와 텍스트 사이의 고정된 공간 */
  margin-left: -1em; /* 이 값을 조정하여 마커의 위치를 왼쪽으로 이동 */
}

/* 볼드 텍스트 스타일 */
.tbl-type-1 .font-bold {
  font-weight: bold;
}

/* 일반 텍스트 스타일 */
.tbl-type-1 .font-normal {
  font-weight: normal;
}

/* 특정 크기의 폰트에 대한 스타일 */
.tbl-type-1 .font-16 {
  font-size: 16px;
}

/* 작은 텍스트 스타일 */
.tbl-type-1 .font-13 {
  font-size: 13px;
}

/* 정의 리스트 스타일 */
.tbl-type-1 dl {
  margin: 0;
  padding: 0;
}

/* 정의 설명 스타일 */
.tbl-type-1 dd {
  margin-left: 20px; /* 설명의 왼쪽 여백 */
}

/* 각 항목 간의 마진을 조정합니다. */
.tbl-type-1 .ml10 {
  margin-left: 10px;
}

.tbl-type-1 .ml60 {
  margin-left: 60px;
}

</style>