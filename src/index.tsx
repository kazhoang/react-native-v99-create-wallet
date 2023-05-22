import {
  requireNativeComponent,
  UIManager,
  Platform,
  ViewStyle,
} from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-v99-create-wallet' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

type NativeCreateWalletProps = {
  color: string;
  style: ViewStyle;
};

const ComponentName = 'NativeCreateWalletView';

export const NativeCreateWalletView =
  UIManager.getViewManagerConfig(ComponentName) != null
    ? requireNativeComponent<NativeCreateWalletProps>(ComponentName)
    : () => {
        throw new Error(LINKING_ERROR);
      };
