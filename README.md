# bicycle-spring
자전거 등록번호를 생성하는 시스템을 구현함

# 구조

## 첫 페이지
등록번호로 해당 자전거를 소유한 사용자를 검색할 수 있다

## 사용자 페이지 (/member/<사용자 아이디>)
사용자가 가지고 있는 자전거의 리스트를 보여준다. 자전거의 이미지는 등록번호를 누르면 나온다.

## 자전거 등록 페이지 (/register_bicycle, 로그인 한정)
로그인한 사용자는 자신의 자전거를 해당 폼으로 등록할 수 있다.

## 사진 표시 (/upload/<등록번호>/<이미지 파일 이름>)
자전거 사진은 해당 프로젝트의 /upload 폴더에 저장된다.