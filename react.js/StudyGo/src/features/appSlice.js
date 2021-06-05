import { createSlice } from '@reduxjs/toolkit';
import { useAuthState } from 'react-firebase-hooks/auth';
import { auth, db } from '../firebase';

const initialState = {
  groupId: null,
  channelId: null,
};

export const appSlice = createSlice({
  name: 'app',
  initialState,
  // The `reducers` field lets us define reducers and generate associated actions
  reducers: {
    enterGroup: (state, action) => {
      state.groupId = action.payload.groupId;
    },
    enterChannel: (state, action) => {
      state.channelId = action.payload.channelId;
    },
  },
});

export const { enterGroup: enterGroup } = appSlice.actions;

export const selectGroupId = (state) => state.app.groupId;

export const { enterChannel: enterChannel } = appSlice.actions;

export const selectChannelId = (state) => state.app.channelId;

export default appSlice.reducer;
