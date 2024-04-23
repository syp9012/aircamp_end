package com.ac.aircamp.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("campApi")
public class CampApi {
	String facltNm;
    String lineIntro;
    String intro;
    String allar;
    String insrncAt;
    String facltDivNm;
    String hvofBgnde;
    String hvofEnddle;
    String featureNm ;
    String induty;
    String lctCl;
    String doNm;
    String sigunguNm ;
    String zipcode ;
    String addr1 ;
    String addr2 ;
    String mapX ;
    String mapY ;
    String tel ;
    String homepage;
    String resveUrl ;
    String resveCl;
    String gnrlSiteCo;
    String autoSiteCo;
    String glampSiteCo ;
    String caravSiteCo ;
    String indvdlCaravSiteCo ;
    String caravInnerFclty ;
    String operPdCl ;
    String operDeCl ;
    String toiletCo ;
    String swrmCo ;
    String wtrplCo ;
    String sbrsCl ;
    String posblFcltyCl ;
    String eqpmnLendCl;
    String animalCmgCl ;
    String firstImageUrl ;
   
	
}
