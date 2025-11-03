import re

# Fix fragment_gallery.xml Chips
with open('app/src/main/res/layout/fragment_gallery.xml', 'r', encoding='utf-8') as f:
    content = f.read()

# Fix Chip elements missing layout_width and layout_height
content = re.sub(
    r'<com\.google\.android\.material\.chip\.Chip\s+(?![^>]*android:layout_width)([^>]*?)/>',
    r'<com.google.android.material.chip.Chip android:layout_width="wrap_content" android:layout_height="wrap_content" \1/>',
    content
)

with open('app/src/main/res/layout/fragment_gallery.xml', 'w', encoding='utf-8') as f:
    f.write(content)

print("Fixed all Chip elements in fragment_gallery.xml")
