import os
import re

# Directory containing layout files
layout_dir = r'c:\Users\lab_services_student\AndroidStudioProjects\AnimalsInDistress\app\src\main\res\layout'

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
        lines = f.readlines()
    
    fixed_lines = []
    for line in lines:
        # If line has layout_width twice, keep only the first occurrence
        if 'android:layout_width' in line and line.count('android:layout_width') > 1:
            # Find first occurrence
            first_idx = line.find('android:layout_width')
            # Find second occurrence
            second_idx = line.find('android:layout_width', first_idx + 1)
            if second_idx > 0:
                # Remove from second occurrence to end of that attribute (including height if present)
                # Pattern: remove ' android:layout_width="match_parent" android:layout_height="wrap_content"' at the end
                line = re.sub(r'\s+android:layout_width="[^"]*"\s+android:layout_height="[^"]*"(?=\s*/?>)', '', line, count=1)
        
        # Same for layout_height if it appears twice
        if 'android:layout_height' in line and line.count('android:layout_height') > 1:
            # Keep first, remove subsequent
            parts = line.split('android:layout_height')
            if len(parts) > 2:
                # Reconstruct with only first two parts
                line = 'android:layout_height'.join(parts[:2]) + parts[2].split('"')[1] if '"' in parts[2] else parts[2]
        
        fixed_lines.append(line)
    
    with open(file_path, 'w', encoding='utf-8') as f:
        f.writelines(fixed_lines)
    
    print(f"Fixed {filename}")

print("\nDone!")
