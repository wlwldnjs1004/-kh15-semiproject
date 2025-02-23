package com.kh.spring09.vo;

import lombok.Data;

//VO(Value Object),필요에 의해 만드는 묶음 데이터

@Data
public class PageVO {
	//필드 - 계산에 필요한 코어 데이터
	private String column, keyword;//검색 정보
	private int page=1;//현재 페이지 번호
	private int size=10;//표시할 게시글 수
	private int count;//전체 게시글 수
	private int blockSize=10;//표시할 블럭 개수
	
	//검색인지 확인
	public boolean isSearch() {
		return column != null && keyword != null;
	}
	//목록인지 확인
	public boolean isList() {
		return !isSearch();
	}
	//시작 rownum과 종료 rownum 번호를 계산
	public int getStartRownum() {
		return page * size - (size-1);
	}
	public int getFinishRownum() {
		return page * size;
	}
	//시작 block과 종료 block의 번호를 계산
	public int getStartBlock() {
		//return (page-1) / 10 * 10 + 1;
		return (page-1) / blockSize * blockSize + 1;
	}
	public int getFinishBlock() {
		//return (page-1) / 10 * 10 + 10;
		int number = (page-1) / blockSize * blockSize + blockSize;
		return Math.min(number, getPageCount());
	}
	//페이지 수 계산
	public int getPageCount() {
		return (count-1) / size + 1;
	}
	//이전 블럭 계산
	public int getPrevBlock() {
		return getStartBlock() - 1;
	}
	//다음 블럭 계산
	public int getNextBlock() {
		return getFinishBlock() + 1;
	}
	public boolean hasPrevBlock() {
		return getStartBlock() > 1;
	}
	public boolean isFirstBlock() {
		return getStartBlock() == 1;
	}
	public boolean hasNextBlock() {
		return getFinishBlock() < getPageCount();
	}
	public boolean isLastBlock() {
		return getFinishBlock() == getPageCount();
	}
//링크를 위한 주소 생성 메소드
	public String getParameters() {
		if(isList()) {
			return"size="+size;
		}
		else {
			return"column="+column+"&keyword="+keyword+"&size="+size;
		}
	}
}




