import * as React from 'react';

import { StyleSheet, View } from 'react-native';
import { NativeCreateWalletView } from 'react-native-v99-create-wallet';

export default function App() {
  // React.useLayoutEffect(() => {
  //   NativeCreateWallet.showView();
  // }, []);

  console.log('init');

  return (
    <View style={styles.container}>
      <NativeCreateWalletView
        style={styles.box}
        onClickCreateButton={(data) => {
          console.log('trigger', data.nativeEvent.data);
        }}
      />
      {/* <Button
        onPress={() => NativeCreateWallet.showView()}
        title={'Open Native Screens'}
      />
      <Button
        onPress={() => NativeCreateWallet.showViewNavigateTo('third')}
        title={'Open Native Another Screen'}
      /> */}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: '100%',
    height: '100%',
  },
});
