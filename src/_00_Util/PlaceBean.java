package _00_Util;

import _00_Util.BeanObject;

public class PlaceBean extends BeanObject {

	public PlaceBean(){
		super("place"
			,new String[]{"placeId","typeId","name"   ,"phone"  ,"address","longitude","latitude","link"   ,"filename","picture" ,"comment"}
			,new String[]{"int"    ,"int"   ,"varchar","varchar","varchar","double"   ,"double"  ,"varchar","varchar" ,"longblob","longtext"}
			,new int[]{11       ,11      ,255      ,255      ,255      ,0          ,0         ,255      ,255       ,0         ,0}
			,new Boolean[]{true     ,false  ,false    ,false   ,false    ,false     ,false     ,false    ,false     ,false    ,false}
			,new Boolean[]{true     ,false  ,false    ,false   ,false    ,false     ,false     ,false    ,false     ,false    ,false}
			,new String[]{null      ,"null"  ,"null"   ,"null"   ,"null"   ,"null"     ,"null"    ,"null"    ,"null"    ,null       ,null}
			,"placeId"
			);

    }



	
}
