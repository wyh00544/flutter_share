#import "SharePlugin.h"
#if __has_include(<share/share-Swift.h>)
#import <share/share-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "share-Swift.h"
#endif

@implementation SharePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftSharePlugin registerWithRegistrar:registrar];
}
@end
