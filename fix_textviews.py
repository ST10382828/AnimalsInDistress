import re

# Read the file
with open('app/src/main/res/layout/fragment_about.xml', 'r', encoding='utf-8') as f:
    content = f.read()

# Fix TextView elements missing layout_width and layout_height
# This regex finds TextView tags that don't have layout_width
content = re.sub(
    r'<TextView\s+(?![^>]*android:layout_width)([^>]*?)>',
    r'<TextView android:layout_width="wrap_content" android:layout_height="wrap_content" \1>',
    content
)

# Write back
with open('app/src/main/res/layout/fragment_about.xml', 'w', encoding='utf-8') as f:
    f.write(content)

print("Fixed all TextView elements missing layout dimensions")
