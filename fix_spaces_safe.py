import re

# Read the file
with open('app/src/main/res/layout/fragment_about.xml', 'r', encoding='utf-8') as f:
    content = f.read()

# Only replace in <Space> elements, not in text content
# This regex matches Space elements with layout_width="0dp"
content = re.sub(
    r'(<Space\s+[^>]*?)android:layout_width="0dp"([^>]*?>)',
    r'\1android:layout_width="match_parent"\2',
    content
)

# Write back
with open('app/src/main/res/layout/fragment_about.xml', 'w', encoding='utf-8') as f:
    f.write(content)

print("Fixed all Space elements with layout_width='0dp' -> 'match_parent'")
