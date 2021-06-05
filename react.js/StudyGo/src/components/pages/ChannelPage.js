import React from 'react';
import ChannelBuildPost from './ChannelBuildPost';
import ChannelHeader from './ChannelHeader';
import './ChannelPage.css';
import PostItem from './PostItem';
import FlipMove from 'react-flip-move';
import { useSelector } from 'react-redux';
import { selectChannelId } from '../../features/appSlice';
import { useCollection, useDocument } from 'react-firebase-hooks/firestore';
import { auth, db } from '../../firebase';
import { useAuthState } from 'react-firebase-hooks/auth';

function ChannelPage() {
  const [user] = useAuthState(auth);
  const channelId = useSelector(selectChannelId);

  const [channel] = useDocument(
    channelId && db.collection('channels').doc(channelId)
  );
  const [channelPosts] = useCollection(
    channelId &&
    db
      .collection('channels')
      .doc(channelId)
      .collection('posts')
      .orderBy('time', 'desc')
  );

  return (
    <div className='channelPage'>
      {channel && (
        <>
          <ChannelHeader
            name={channel?.data()?.name}
            description={channel?.data()?.description}
            urlPic={channel?.data()?.urlPic}
          />
          {channel?.data()?.creator === user.email && <ChannelBuildPost />}

          <FlipMove>
            {channelPosts?.docs?.map((doc) => {
              const {
                email,
                deadline,
                input,
                postType,
                time,
                urlFile,
              } = doc.data();
              return (
                <PostItem
                  key={doc.id}
                  name={channel?.data()?.name}
                  urlPic={channel?.data()?.urlPic}
                  time={time}
                  message={input}
                  deadline={deadline}
                  admin={email}
                  postType={postType}
                  urlFile={urlFile}
                  postId={doc.id}
                />
              );
            })}
          </FlipMove>
        </>
      )}
    </div>
  );
}

export default ChannelPage;
