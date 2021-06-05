import React from 'react';
import { useCollection, useDocument } from 'react-firebase-hooks/firestore';
import { auth, db } from '../../firebase';
import { useAuthState } from 'react-firebase-hooks/auth';
import FeedbackItem from './FeedbackItem';

function Feedbacks() {
 const [user] = useAuthState(auth);
 const [feedback] = useCollection(user.email && db.collection('users').doc(user.email).collection('feedbacks').orderBy('time', 'desc'));

 return (
  <div>
   {feedback?.docs?.map((doc) => {
    const { channelId, postId, submitId, time } = doc?.data();
    return < FeedbackItem key={doc.id} channelId={channelId} postId={postId} submitId={submitId} time={time} />
   })}
  </div>
 )
}

export default Feedbacks