package com.liudi.nettychat.mapper;


import com.liudi.nettychat.vo.MyFriendsVO;

import java.util.List;

public interface UserMapperCustom {
//    List<FriendsRequestVO> queryFriendRequestList(String acceptUserId);
    List<MyFriendsVO> queryMyFriends(String userId);
//    void batchUpdateMsgSigned(List<String> msgIdList);

}
