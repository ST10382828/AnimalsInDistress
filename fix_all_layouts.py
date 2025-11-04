import os
import re

# Directory containing layout files
layout_dir = r'c:\Users\lab_services_student\AndroidStudioProjects\AnimalsInDistress\app\src\main\res\layout'

# Get all fragment XML files
files_to_fix = [
    'fragment_annual_report.xml',
    'fragment_case_study.xml',
    'fragment_contact.xml',
    'fragment_dancing_donkey.xml',
    'fragment_education.xml',
    'fragment_equine_outreach.xml',
    'fragment_faq.xml',
    'fragment_golf_day_2025.xml',
    'fragment_legacies.xml',
    'fragment_monthly_debit.xml',
    'fragment_my_school.xml',
    'fragment_partners.xml',
    'fragment_payroll_giving.xml',
    'fragment_pre_loved.xml',
    'fragment_services.xml',
    'fragment_shop.xml',
    'fragment_tax_certificate.xml',
    'fragment_team.xml',
    'fragment_thousand_heroes.xml'
]

fixed_count = 0

for filename in files_to_fix:
    file_path = os.path.join(layout_dir, filename)
    if not os.path.exists(file_path):
        print(f"Skipping {filename} - file not found")
        continue
    
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # Fix TextViews without layout attributes
    original_content = content
    content = re.sub(
        r'<TextView(\s+)(android:text=)',
        r'<TextView\1android:layout_width="match_parent" android:layout_height="wrap_content" \2',
        content
    )
    
    if content != original_content:
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Fixed {filename}")
        fixed_count += 1
    else:
        print(f"No changes needed for {filename}")

print(f"\nTotal files fixed: {fixed_count}")
