package=$(/Users/admin/Library/Android/sdk/build-tools/30.0.2/aapt dump badging "$*" | awk '/package/{gsub("name=|'"'"'","");  print $2}')
activity=$(/Users/admin/Library/Android/sdk/build-tools/30.0.2/aapt dump badging "$*" | awk '/activity/{gsub("name=|'"'"'","");  print $2}')
echo
echo "   file : $1"
echo "package : $package"
echo "activity: $activity"