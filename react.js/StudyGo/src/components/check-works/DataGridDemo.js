import * as React from 'react';
import { DataGrid } from '@material-ui/data-grid';
import { Button } from '@material-ui/core';
import { useSelector } from 'react-redux';
import { selectChannelId } from '../../features/appSlice';
import { useCollection } from 'react-firebase-hooks/firestore';
import { db } from '../../firebase';
import { useParams } from 'react-router';

const columns = [
  { field: 'name', headerName: 'Name', width: 140 },
  {
    field: "workUrl",
    headerName: "Url Work",
    width: 130,
    renderCell: (params) => (<a href='https://www.google.com/'>link</a>)

  },
  {
    field: "time",
    headerName: "Time",
    width: 130,
  },
  {
    field: 'mark',
    headerName: 'Mark',
    type: 'number',
    width: 100,
    editable: true,
  },
  {
    field: 'feedback',
    headerName: 'Feedback',
    width: 198,
    editable: true,
  },
  {
    field: 'submit',
    headerName: 'Submit',
    type: 'button',
    width: 150,
    renderCell: (params) => (
      <strong>
        <Button
          variant="contained"
          color="primary"
          size="small"
          style={{ marginLeft: 16 }}
        >
          submit
        </Button>
      </strong>
    ),
  }
];


export default function DataGridDemo() {
  const { postId } = useParams();
  const channelId = useSelector(selectChannelId);
  const submitedWorks = useCollection(postId && channelId && db.collection('channels').doc(channelId).collection('posts').doc(postId).collection('submitedWorks'));
  console.log(postId);
  console.log(channelId);

  return (
    <div
      style={{ height: 'calc(100vh - var(--app-bar-height))', width: '100%' }}
    >
    </div>
  );
}
