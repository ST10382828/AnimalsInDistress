with open('app/src/main/res/layout/fragment_about.xml', 'r', encoding='utf-8') as f:
    content = f.read()

content = content.replace('android:layout_width="0dp"', 'android:layout_width="match_parent"')

with open('app/src/main/res/layout/fragment_about.xml', 'w', encoding='utf-8') as f:
    f.write(content)

print("Fixed all Space elements!")
