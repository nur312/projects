import React from 'react';
import { useCollection, useDocument } from 'react-firebase-hooks/firestore';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router';
import { selectChannelId } from '../../features/appSlice';
import { db } from '../../firebase';
import WorkItem from './WorkItem';

function Submits() {
  const { postId } = useParams();
  const channelId = useSelector(selectChannelId);

  const [submits, loading, error] = useCollection(
    channelId &&
    postId &&
    db
      .collection('channels')
      .doc(channelId)
      .collection('posts')
      .doc(postId)
      .collection('submitedWorks')
      .orderBy('isSent')
  );

  if (!submits?.docs || loading || error) {
    return <h3>Submits loading</h3>;
  }

  return (
    <div>
      {submits?.docs.map((doc) => {
        return <WorkItem key={doc.id} id={doc.id} submitDoc={doc} />;
      })}
    </div>
  );
}

export default Submits;
