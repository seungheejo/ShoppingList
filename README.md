# ShoppingList
쇼핑할 때 필요한 쇼핑 리스트를 리스트업 하는 안드로이드 어플리케이션

## 프로젝트 개요
마트나 백화점에 장을 보러 갈 때 사야 하는 물건들을 저장해 두고 잊어버리지 않도록 리스트로 만든다. 
만들어진 리스트로 장을 보러 가서 구입한 물건들은 체크박스에 체크를 하여 구입한 물건과 아직 구입하지 않은 물건들을 구분하여 장을 볼 수 있다.

## 프로젝트 기능
### Splash Screen
* 어플을 켤 때 메인 화면이 켜지기까지 걸리는 로딩시간에 보여지는 대형 이미지이다. 
* xml파일에 splash screen용 이미지를 추가해서 액티비티에서 추가한 이미지를 setContentView로 보여준다. 
* 이미지를 보여주고 Intent로 메뉴를 선택하는 액티비티로 전환한다.

### ActionBar 메뉴
* AndroidManifest.xml에 추가되어 있는 android:theme="@style/AppTheme.NoActionBar" 를 제거해야 ActionBar를 사용할 수 있다. 
* ActionBar에 넣을 메뉴를 xml파일에 옷, 음식, 가구, 가전제품, 해산물의 카테고리 별 이미지를 넣고, 장바구니를 저장할 이미지도 추가한다. 
* 이 파일을 onCreateOptionsMenu 메소드에서 액션버튼 메뉴를 Inflate로 ActionBar에 넣는다. 
* 카테고리 별 이미지들은 처음에 setVisibility(View.INVISIBLE) 로 지정되어 있기 때문에 보이지 않다가 onOptionsItemSelected 메소드에서 ActionBar에 올려져 있는 카테고리를 선택하면 
카테고리에 해당하는 이미지들은 setVisibility(View.VISIBLE )로 변경하고 해당하지 않는 이미지들은setImageResource(android.R.color.transparent) 로 변경해서 안보이게 구현하였다. 

### 드래그 앤 드랍
* ActionBar에 있는 카테고리 이미지를 클릭하면 그 아래에 카테고리에 해당하는 이미지들이 보여진다. 
* 그 이미지를 길게 클릭해서 OnLongClickListener 클래스에서 처리하고 OnDragListener 클래스에서 이미지를 길게 클릭하고 드래그하는 동작들을 분기처리하여 처리해준다. 
* DragEvent.ACTION_DRAG_ENDED 에서 이미지를 드랍한 후에 그 이미지의 카테고리 타입과 아이템 명을 객체에 담아서 리스트에 쌓는다.

### Firebase Realtime Database
* 구글 계정으로 안드로이드 스튜디오에 내장되어있는 웹DB인 Firebase를 사용하였다. 
* FirebaseDatabase 클래스와 DatabaseReference 클래스를 import 하고 setValue로 리스트 데이터를 넣어준다. 
* ValueEventListener 클래스의 onDataChange 메소드에서 DataSnapshot 객체를 이용하여 데이터베이스에 있는 데이터를 가져와서 장바구니에 넣었던 리스트를 볼 수 있게 한다.
