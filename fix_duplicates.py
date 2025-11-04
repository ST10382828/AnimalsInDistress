import os
import re

# Directory containing layout files
layout_dir = r'c:\Users\lab_services_student\AndroidStudioProjects\AnimalsInDistress\app\src\main\res\layout'

# Files that have duplicate attributes
files_to_fix = [
    'fragment_equine_outreach.xml',
    'fragment_education.xml'
]

for filename in files_to_fix:
    file_path = os.path.join(layout_dir, filename)
    if not os.path.exists(file_path):
        print(f"Skipping {filename} - file not found")
        continue
    
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # Remove duplicate layout_width and layout_height attributes
    # Pattern: finds TextView with layout_width/height appearing twice
    content = re.sub(
        r'(<TextView\s+android:layout_width="match_parent"\s+android:layout_height="wrap_content"\s+)(android:layout_width="match_parent"\s+android:layout_height="wrap_content"\s+)',
        r'\1',
        content
    )
    
    with open(file_path, 'w', encoding='utf-8') as f:
        f.write(content)
    print(f"Fixed duplicates in {filename}")

print("\nDone!")
