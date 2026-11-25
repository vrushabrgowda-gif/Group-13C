package com.example.data

object CyberShieldContent {

    val quickSafetyTips = listOf(
        "Pause for 30 seconds before clicking any urgent KYC, reward, or parcel link.",
        "Entering your UPI PIN is ONLY required to send money—never to receive a refund or prize.",
        "Indian police, CBI, or ED never conduct 'Digital Arrests' over WhatsApp or Skype video calls.",
        "Never install .apk files forwarded on WhatsApp or Telegram, even from known contacts.",
        "If cheated financially online, dial 1930 immediately during the golden hour to freeze funds."
    )

    // ==================== 1. PHISHING MODULE ====================

    val phishingExplanation =
        "Phishing is a cyberattack where fraudsters impersonate trusted organizations—such as Indian banks, UPI apps, courier companies, electricity boards, or government portals—to trick you into revealing OTPs, banking passwords, card details, or transferring money.\n\n" +
            "In India, attackers frequently exploit urgency (\"account blocked tonight\") or greed (\"cashback reward\") via SMS, WhatsApp, email, and phone calls."

    val phishingTypes = listOf(
        PhishingType(
            id = "email",
            title = "Email Phishing",
            subtitle = "Fake official notices in your inbox",
            description = "Mass or targeted emails spoofing banks, Income Tax Department, EPFO, or popular services with urgent verification links or malicious attachments.",
            indianExample = "An email claiming 'Your Income Tax Refund of ₹18,450 is pending—click to verify bank account'."
        ),
        PhishingType(
            id = "smishing",
            title = "SMS Phishing (Smishing)",
            subtitle = "Fraudulent text messages with shortened links",
            description = "SMS alerts sent from random 10-digit mobile numbers or lookalike headers threatening immediate suspension of bank accounts, PAN, or electricity supply.",
            indianExample = "SMS: 'Dear Customer, your SBI YONO KYC expires today. Update immediately via bit[.]ly/...'"
        ),
        PhishingType(
            id = "vishing",
            title = "Voice Phishing (Vishing)",
            subtitle = "Manipulative phone calls",
            description = "Callers posing as bank customer care, credit card agents, or telecom executives persuading victims to share OTPs, CVV, or install remote-access apps.",
            indianExample = "A caller offering to 'disable unwanted international charges' on your debit card if you read out the SMS OTP."
        ),
        PhishingType(
            id = "fake_bank",
            title = "Fake Banking Websites",
            subtitle = "Cloned login & netbanking portals",
            description = "Lookalike web pages using subtle spelling tricks (typosquatting) that capture your customer ID, login password, and transaction PIN.",
            indianExample = "Search ads leading to 'hdfcbank-kyc-update[.]example' instead of the official bank domain."
        ),
        PhishingType(
            id = "upi_scam",
            title = "UPI Payment Scams",
            subtitle = "Collect requests & QR code traps",
            description = "Scammers send a UPI 'Collect Request' or QR code claiming you will receive money or a refund, tricking you into entering your UPI PIN—which actually debits your account.",
            indianExample = "A marketplace buyer sends a QR code asking you to 'scan and enter PIN to receive ₹5,000 advance'."
        ),
        PhishingType(
            id = "fake_delivery",
            title = "Fake Delivery Notifications",
            subtitle = "Small-fee parcel release traps",
            description = "Messages pretending a courier or India Post package is stuck due to an incomplete address, asking you to pay ₹5–₹25 on a fake card/UPI capture page.",
            indianExample = "SMS: 'Your parcel is on hold at hub. Pay ₹5 redelivery fee to avoid return.'"
        )
    )

    val phishingRedFlags = listOf(
        PhishingRedFlag(
            id = "urls",
            title = "Suspicious & Lookalike URLs",
            description = "Misspelled domains, extra hyphens, unfamiliar extensions, or URL shorteners hiding the real website.",
            exampleCue = "hdfc-kyc-verify[.]example instead of official bank domain"
        ),
        PhishingRedFlag(
            id = "spelling",
            title = "Spelling & Grammar Errors",
            description = "Awkward phrasing, inconsistent capitalization, or generic greetings like 'Dear Valued User'.",
            exampleCue = "'Your A/C will suspend within 2 hrs kindly click fast'"
        ),
        PhishingRedFlag(
            id = "urgency",
            title = "Artificial Urgency & Fear",
            description = "Threats of immediate account freeze, SIM deactivation, or power cut to stop you from verifying calmly.",
            exampleCue = "'Account blocked today at 9:30 PM if not verified'"
        ),
        PhishingRedFlag(
            id = "rewards",
            title = "Too-Good-To-Be-True Rewards",
            description = "Unexpected lottery prizes, ₹50,000 cashback offers, or free government scheme payouts.",
            exampleCue = "'Congratulations! Claim ₹50,000 reward within 15 minutes'"
        ),
        PhishingRedFlag(
            id = "otp",
            title = "Requests for OTP, PIN, or CVV",
            description = "No legitimate bank, courier, or government official will ever ask for your OTP, UPI PIN, or card CVV.",
            exampleCue = "'Share 6-digit verification code to cancel transaction'"
        ),
        PhishingRedFlag(
            id = "attachments",
            title = "Suspicious Attachments (.apk / .zip)",
            description = "Unexpected files such as 'eChallan.apk', 'Invoice.zip', or macro-enabled documents sent via chat or email.",
            exampleCue = "RTO_Traffic_Fine.apk sent on WhatsApp"
        )
    )

    val phishingScenarios = listOf(
        PhishingScenario(
            id = "phish_1",
            channel = "SMS Alert",
            senderLabel = "+91 98XXXXXX14 (Unknown Number)",
            messageBody = "Your bank account will be blocked today. Verify your KYC immediately using this link.",
            simulatedUrlDisplay = "hxxp://bank-kyc-update-alert[.]example [NON-CLICKABLE DEMO]",
            isSuspicious = true,
            warningSignOptions = listOf(
                "Artificial urgency threatening account block + non-official lookalike link from a mobile number",
                "The message uses polite formal wording and mentions KYC",
                "It was received during banking hours"
            ),
            correctWarningSignIndex = 0,
            nextActionOptions = listOf(
                "Click the link quickly and enter your netbanking password",
                "Do not click; verify directly inside your official bank app or visit your branch",
                "Reply 'STOP' with your account number"
            ),
            correctNextActionIndex = 1,
            explanation = "Banks never ask customers to complete KYC through random mobile-number SMS links with threats of same-day blocking. Always check status directly in your verified banking app."
        ),
        PhishingScenario(
            id = "phish_2",
            channel = "WhatsApp / SMS",
            senderLabel = "PRIZE-DESK-IN",
            messageBody = "Congratulations! You have won a ₹50,000 reward. Claim your prize now.",
            simulatedUrlDisplay = "hxxp://claim-lucky-reward-in[.]example [NON-CLICKABLE DEMO]",
            isSuspicious = true,
            warningSignOptions = listOf(
                "Unsolicited ₹50,000 prize claim with an external link pressuring immediate action",
                "The currency symbol ₹ is used properly",
                "No attachments were included"
            ),
            correctWarningSignIndex = 0,
            nextActionOptions = listOf(
                "Enter your UPI PIN on the page to receive the ₹50,000 prize",
                "Forward the offer to family groups so they can claim too",
                "Ignore, delete, and block the sender—never enter PIN or bank details for prize claims"
            ),
            correctNextActionIndex = 2,
            explanation = "Unexpected cash rewards are classic bait to steal card/UPI credentials or trick victims into approving a UPI debit request. You never enter a UPI PIN to receive money."
        ),
        PhishingScenario(
            id = "phish_3",
            channel = "SMS Delivery Alert",
            senderLabel = "DLVRY-HUB",
            messageBody = "Your parcel is on hold. Pay ₹5 to release your delivery.",
            simulatedUrlDisplay = "hxxp://parcel-release-fee[.]example [NON-CLICKABLE DEMO]",
            isSuspicious = true,
            warningSignOptions = listOf(
                "Small ₹5 fee lure designed to harvest full debit/credit card details and OTPs",
                "Couriers often send tracking updates via SMS",
                "The amount is too small to be risky"
            ),
            correctWarningSignIndex = 0,
            nextActionOptions = listOf(
                "Pay ₹5 using your primary credit card since it's a tiny amount",
                "Check tracking directly on the official courier website/app where you placed the order",
                "Call the sender number and share your card CVV"
            ),
            correctNextActionIndex = 1,
            explanation = "Scammers use tiny ₹5–₹10 fees so victims drop their guard and type card numbers, CVV, and OTP on a cloned payment gateway—allowing attackers to drain much larger amounts."
        ),
        PhishingScenario(
            id = "phish_4",
            channel = "Official Bank Awareness",
            senderLabel = "Verified Bank Security Notice",
            messageBody = "Security Reminder: Your bank NEVER asks for your OTP, ATM PIN, CVV, or UPI PIN over phone, SMS, or email. Always log in only through the official app.",
            simulatedUrlDisplay = null,
            isSuspicious = false,
            warningSignOptions = listOf(
                "No suspicious link, no demand for money, and no request for OTP/credentials—purely informational",
                "It mentions OTP and UPI PIN, so it must be phishing",
                "Any security message from a bank is automatically malicious"
            ),
            correctWarningSignIndex = 0,
            nextActionOptions = listOf(
                "Read and follow the safety advice; no action or link click is needed",
                "Call back to share your debit card expiry date",
                "Delete your banking app immediately"
            ),
            correctNextActionIndex = 0,
            explanation = "This message is legitimate awareness communication: it asks for zero personal data, contains no suspicious external URLs, and creates no artificial panic."
        ),
        PhishingScenario(
            id = "phish_5",
            channel = "SMS Threat",
            senderLabel = "+91 97XXXXXX88",
            messageBody = "Dear consumer, your electricity connection will be disconnected tonight at 9:30 PM because your last month bill was not updated. Call 98XXXXXX12 immediately.",
            simulatedUrlDisplay = null,
            isSuspicious = true,
            warningSignOptions = listOf(
                "Threatening disconnection tonight + asking to call an unverified personal mobile number",
                "Mentions electricity bill which every household pays",
                "Mentions a specific time of night"
            ),
            correctWarningSignIndex = 0,
            nextActionOptions = listOf(
                "Call the mobile number and install any support app they suggest",
                "Check your official electricity board app/portal or printed bill helpline directly",
                "Send ₹10 via UPI to the number to test if it's real"
            ),
            correctNextActionIndex = 1,
            explanation = "Electricity disconnection scams rely on late-evening panic so victims call the scammer's personal number and get tricked into installing screen-sharing apps."
        )
    )

    // ==================== 2. DIGITAL ARREST MODULE ====================

    val digitalArrestAwarenessMessage =
        "Law enforcement agencies do not conduct legitimate arrests through video calls or demand payments to settle criminal allegations."

    val digitalArrestExplanation =
        "A 'Digital Arrest' scam is a form of cyber extortion where fraudsters pose as police officers, CBI, Enforcement Directorate (ED), Narcotics Bureau, TRAI, or Customs officials over phone or video calls.\n\n" +
            "They falsely accuse the victim of crimes—such as an illegal parcel, money laundering, or SIM misuse—and claim the victim is 'under digital arrest' until they transfer money for verification or clearance. Under Indian law, there is no legal concept called 'digital arrest'."

    val digitalArrestSteps = listOf(
        DigitalArrestStep(
            1,
            "Initial Contact via Call or IVR",
            "Victim receives an automated or live call claiming their Aadhaar, SIM card, or courier parcel is linked to illegal goods or financial fraud."
        ),
        DigitalArrestStep(
            2,
            "Impersonation of Officials",
            "The call is 'transferred' to someone on WhatsApp/Skype video wearing a fake police or CBI uniform with forged emblems and fabricated ID cards."
        ),
        DigitalArrestStep(
            3,
            "False Criminal Allegations",
            "Scammers send fake arrest warrants, RBI notices, or FIR PDFs bearing the victim's name to create intense shock and fear."
        ),
        DigitalArrestStep(
            4,
            "Psychological Pressure & Isolation",
            "Victims are ordered to keep their camera on continuously, stay alone in a room, and not speak to family members or lawyers ('confidential national investigation')."
        ),
        DigitalArrestStep(
            5,
            "Demands for Money",
            "Fraudsters demand immediate transfers to 'RBI escrow accounts' or 'court verification accounts', promising the money will be refunded after clearance."
        )
    )

    val digitalArrestWarningSigns = listOf(
        "Threatening WhatsApp, Skype, or Zoom video calls from unknown numbers",
        "Fake police uniforms, studio-style police station backdrops, and forged ID cards",
        "Demands for immediate money transfers (UPI, IMPS, RTGS, NEFT) to 'prove innocence'",
        "Instructions to remain continuously on camera and not inform family or local police",
        "Requests for confidential banking balances, OTPs, Aadhaar, or PAN details"
    )

    val digitalArrestScenarios = listOf(
        DigitalArrestScenario(
            id = "da_1",
            title = "Fake Police Video Call & Payment Demand",
            callerClaim = "Inspector claiming CBI Cyber Crime Investigation",
            promptText = "You receive a video call from someone claiming to be a police officer. They accuse you of being involved in a criminal investigation and demand immediate payment.",
            options = listOf(
                DigitalArrestOption(
                    id = "da_1_opt1",
                    label = "Transfer money immediately to avoid arrest.",
                    isSafe = false,
                    feedback = "Unsafe choice. Police, CBI, or courts NEVER demand online money transfers over a call to close a case or verify bank balances. Once transferred, scammers quickly move the funds."
                ),
                DigitalArrestOption(
                    id = "da_1_opt2",
                    label = "Share personal and banking information to prove innocence.",
                    isSafe = false,
                    feedback = "Unsafe choice. Sharing Aadhaar, PAN, bank account numbers, or OTPs allows fraudsters to hijack your accounts or commit identity theft."
                ),
                DigitalArrestOption(
                    id = "da_1_opt3",
                    label = "End the call, avoid making payments, and independently contact the appropriate authorities.",
                    isSafe = true,
                    feedback = "Correct and safe response! Disconnect immediately, do not panic or pay, inform a trusted family member, and verify or report via the National Cybercrime Helpline 1930 or your local police station."
                )
            ),
            keyTakeaway = "Legitimate police inquiries follow formal written procedures—never video-call confinement or instant UPI/RTGS settlements."
        ),
        DigitalArrestScenario(
            id = "da_2",
            title = "Fake Customs Parcel & 'Stay on Camera' Order",
            callerClaim = "Customs & Narcotics Officer",
            promptText = "A caller claims an international parcel in your name contains illegal items. They switch to video call, show a fake warrant, and instruct you not to hang up or tell anyone while they 'record your statement'.",
            options = listOf(
                DigitalArrestOption(
                    id = "da_2_opt1",
                    label = "Stay on the video call alone for hours as instructed.",
                    isSafe = false,
                    feedback = "Unsafe choice. Isolation is the core tactic of Digital Arrest scams—keeping you on camera prevents you from checking facts with family or real authorities."
                ),
                DigitalArrestOption(
                    id = "da_2_opt2",
                    label = "Disconnect the call right away, talk to family, and report the caller at cybercrime.gov.in or 1930.",
                    isSafe = true,
                    feedback = "Correct! Disconnecting breaks the psychological trap immediately. Real law enforcement never keeps citizens locked on a video call."
                ),
                DigitalArrestOption(
                    id = "da_2_opt3",
                    label = "Send a photo of your Aadhaar and bank passbook to clear your name.",
                    isSafe = false,
                    feedback = "Unsafe choice. Sending identity and banking photos gives scammers everything they need for financial fraud and future impersonation."
                )
            ),
            keyTakeaway = "Breaking isolation—hanging up and speaking to someone you trust—instantly defeats digital arrest pressure."
        ),
        DigitalArrestScenario(
            id = "da_3",
            title = "TRAI SIM Disconnection Escalation",
            callerClaim = "Telecom Regulatory Officer transferring to 'Cyber Police'",
            promptText = "An automated voice warns your mobile number will be blocked in 2 hours due to illegal ads, and asks you to press 9 to speak with an officer who demands an 'RBI verification deposit'.",
            options = listOf(
                DigitalArrestOption(
                    id = "da_3_opt1",
                    label = "Press 9 and provide your bank details for verification.",
                    isSafe = false,
                    feedback = "Unsafe choice. TRAI does not disconnect individual SIMs via automated threat calls or transfer callers to police for money deposits."
                ),
                DigitalArrestOption(
                    id = "da_3_opt2",
                    label = "Pay a refundable security deposit so your SIM stays active.",
                    isSafe = false,
                    feedback = "Unsafe choice. There is no such thing as an 'RBI verification deposit' or refundable arrest clearance fee."
                ),
                DigitalArrestOption(
                    id = "da_3_opt3",
                    label = "Hang up, block the number, and report the suspected fraud call on Chakshu (Sanchar Saathi) or 1930.",
                    isSafe = true,
                    feedback = "Correct! Ignoring the automated prompt and reporting suspicious numbers helps telecom authorities block scam networks."
                )
            ),
            keyTakeaway = "Government regulators and RBI never ask citizens to transfer funds to 'safe' or 'verification' accounts."
        )
    )

    // ==================== 3. MALICIOUS APPS MODULE ====================

    val maliciousAppTopics = listOf(
        "What Are Malicious Applications?" to
            "Malicious apps are rogue software disguised as useful utilities, games, government schemes, or banking updates. Once installed, they silently steal OTPs, banking credentials, contacts, and personal photos.",
        "Fake APK Installation Risks" to
            "Fraudsters send Android Package (.apk) files on WhatsApp or Telegram—named things like 'RTO_Challan.apk', 'PM_Yojana.apk', or 'Wedding_Card.apk'. Sideloading APKs bypasses Play Store security checks.",
        "Fake Loan Applications" to
            "Unverified instant-loan apps promise quick cash with zero paperwork, then demand Contacts and Photo Gallery access to harass and blackmail borrowers—even after repayment.",
        "Spyware & Banking Malware" to
            "Spyware secretly records keystrokes, reads incoming SMS messages (including bank OTPs), and logs microphone or location data without the user noticing.",
        "Screen-Sharing App Abuse" to
            "Scammers trick victims into installing legitimate remote-support apps (like screen-sharing tools) during fake customer-care calls so they can watch the victim's screen and capture OTPs or trigger UPI transfers.",
        "Unnecessary App Permissions" to
            "A harmless utility like a calculator, flashlight, or wallpaper app has no valid reason to request Contacts, SMS, Call Logs, or Microphone access.",
        "SMS, Contacts & Accessibility Misuse" to
            "SMS permission lets malware intercept banking OTPs; Accessibility permission is especially dangerous because it lets an app read everything on your screen and simulate taps inside banking apps."
    )

    val sampleAppProfiles = listOf(
        SampleAppProfile(
            id = "calculator",
            appName = "QuickCalc Pro (Hypothetical Example)",
            category = "Utility / Calculator",
            trustNote = "Suspicious: A basic calculator only performs local arithmetic and needs zero personal data permissions.",
            isSuspiciousApp = true,
            permissions = listOf(
                AppPermissionDetail(
                    "Camera Access",
                    PermissionRiskLevel.SUSPICIOUS,
                    "A standard calculator has no need to capture photos or video in the background."
                ),
                AppPermissionDetail(
                    "Contacts Access",
                    PermissionRiskLevel.HIGH_RISK,
                    "Allows harvesting phone numbers of your family and colleagues for spam or extortion."
                ),
                AppPermissionDetail(
                    "Microphone Access",
                    PermissionRiskLevel.HIGH_RISK,
                    "Could permit covert audio recording while you speak sensitive details."
                ),
                AppPermissionDetail(
                    "SMS Access (Read/Receive)",
                    PermissionRiskLevel.HIGH_RISK,
                    "Enables silent reading of banking OTPs and two-factor authentication codes."
                )
            )
        ),
        SampleAppProfile(
            id = "loan_apk",
            appName = "InstantCash 5-Min Loan (Unverified APK)",
            category = "Unverified Finance APK",
            trustNote = "High Risk: Sideloaded loan apps outside RBI-regulated entities frequently abuse personal data for harassment.",
            isSuspiciousApp = true,
            permissions = listOf(
                AppPermissionDetail(
                    "Contacts & Call Logs",
                    PermissionRiskLevel.HIGH_RISK,
                    "Commonly harvested by predatory loan apps to message or threaten everyone in your address book."
                ),
                AppPermissionDetail(
                    "Photos & Media Storage",
                    PermissionRiskLevel.HIGH_RISK,
                    "Can exfiltrate private photos for extortion scams."
                ),
                AppPermissionDetail(
                    "Precise Location",
                    PermissionRiskLevel.SUSPICIOUS,
                    "Excessive continuous tracking beyond basic verification."
                ),
                AppPermissionDetail(
                    "SMS Access",
                    PermissionRiskLevel.HIGH_RISK,
                    "Reads financial alerts, transaction histories, and bank balances."
                )
            )
        ),
        SampleAppProfile(
            id = "flashlight",
            appName = "BrightTorch LED (Hypothetical Example)",
            category = "Device Utility",
            trustNote = "Suspicious: Modern Android phones already have a built-in flashlight toggle—third-party torch apps asking for Accessibility or SMS are major red flags.",
            isSuspiciousApp = true,
            permissions = listOf(
                AppPermissionDetail(
                    "Accessibility Service",
                    PermissionRiskLevel.HIGH_RISK,
                    "Can read screen content, capture passwords, and tap buttons inside other apps automatically."
                ),
                AppPermissionDetail(
                    "SMS Access",
                    PermissionRiskLevel.HIGH_RISK,
                    "Completely unnecessary for turning on an LED flash."
                ),
                AppPermissionDetail(
                    "Contacts Access",
                    PermissionRiskLevel.SUSPICIOUS,
                    "Unrelated to flashlight functionality."
                )
            )
        ),
        SampleAppProfile(
            id = "verified_upi",
            appName = "Official Bank / UPI App (Verified Store)",
            category = "Verified Banking",
            trustNote = "Contextual: Legitimate banking apps request targeted permissions with clear purposes and never ask for Accessibility control.",
            isSuspiciousApp = false,
            permissions = listOf(
                AppPermissionDetail(
                    "Camera (While Using App)",
                    PermissionRiskLevel.CONTEXTUAL,
                    "Legitimate when you actively scan a merchant UPI QR code."
                ),
                AppPermissionDetail(
                    "Phone State / SMS (SIM Binding)",
                    PermissionRiskLevel.CONTEXTUAL,
                    "Used once during UPI registration to verify your registered SIM card with the bank."
                ),
                AppPermissionDetail(
                    "Accessibility Service",
                    PermissionRiskLevel.CONTEXTUAL,
                    "Not requested by legitimate banking apps—in fact, banks warn against enabling Accessibility for unknown apps."
                )
            )
        )
    )

    val safeAppChecklist = listOf(
        SafeAppChecklistItem(
            "chk_store",
            "Download applications from trusted app stores",
            "Prefer official stores like Google Play Store with Play Protect enabled."
        ),
        SafeAppChecklistItem(
            "chk_dev",
            "Check the developer's identity",
            "Verify the official company name, website, and verified developer email."
        ),
        SafeAppChecklistItem(
            "chk_perm",
            "Review application permissions",
            "Deny permissions like SMS, Contacts, or Microphone that do not match the app's purpose."
        ),
        SafeAppChecklistItem(
            "chk_apk",
            "Avoid installing unknown APK files",
            "Never tap .apk files received via WhatsApp, Telegram, or SMS links."
        ),
        SafeAppChecklistItem(
            "chk_reviews",
            "Check reviews carefully",
            "Read recent 1-star reviews to spot reports of hidden charges, spam, or harassment."
        ),
        SafeAppChecklistItem(
            "chk_os",
            "Keep the operating system updated",
            "Install Android security patches promptly to close known app and system vulnerabilities."
        ),
        SafeAppChecklistItem(
            "chk_uninstall",
            "Uninstall suspicious applications",
            "Remove unused or suspicious apps immediately and revoke Accessibility/Device Admin rights."
        )
    )

    val permissionRiskCombos = listOf(
        PermissionRiskCombo(
            id = "sms",
            permissionTag = "Read SMS",
            summary = "OTP Interception Risk",
            abuseExample = "Can silently read incoming bank OTPs, transaction alerts, and password reset codes."
        ),
        PermissionRiskCombo(
            id = "contacts",
            permissionTag = "Contacts & Call Logs",
            summary = "Social Engineering & Extortion Risk",
            abuseExample = "Harvests your entire family/work network for impersonation scams or loan-app harassment."
        ),
        PermissionRiskCombo(
            id = "accessibility",
            permissionTag = "Accessibility Service",
            summary = "Critical Device Takeover Risk",
            abuseExample = "Allows a malicious app to read passwords on screen, bypass warnings, and perform automated taps in payment apps."
        ),
        PermissionRiskCombo(
            id = "camera_mic",
            permissionTag = "Camera & Microphone",
            summary = "Surveillance & Privacy Risk",
            abuseExample = "Enables unauthorized recording of surroundings, conversations, or sensitive documents."
        ),
        PermissionRiskCombo(
            id = "screen_share",
            permissionTag = "Screen Sharing / Cast",
            summary = "Live Credential Exposure Risk",
            abuseExample = "During fake support calls, attackers watch your screen live as you enter PINs, CVVs, or OTPs."
        )
    )

    // ==================== 4. 16-QUESTION QUIZ ====================

    val quizQuestions = listOf(
        QuizQuestion(
            id = 1,
            category = ThreatModuleId.PHISHING,
            question = "You receive an SMS stating your bank account will be blocked today unless you update KYC via a shortened link. What should you do?",
            options = listOf(
                "Click the link quickly and enter your customer ID",
                "Ignore the link and verify directly in your official bank app or branch",
                "Reply to the SMS with your PAN number",
                "Forward the link to friends to ask if it works"
            ),
            correctIndex = 1,
            explanation = "Banks do not threaten same-day account suspension via random SMS links. Always verify directly using the official app or branch."
        ),
        QuizQuestion(
            id = 2,
            category = ThreatModuleId.PHISHING,
            question = "When receiving money via UPI from a buyer or refund desk, when are you required to enter your UPI PIN?",
            options = listOf(
                "Every time you receive more than ₹1,000",
                "Only after scanning their QR code",
                "Never—entering a UPI PIN is only for sending money out of your account",
                "Only for government refunds"
            ),
            correctIndex = 2,
            explanation = "A UPI PIN only authorizes outgoing payments. Any request asking you to enter your PIN to 'receive' money is a scam."
        ),
        QuizQuestion(
            id = 3,
            category = ThreatModuleId.PHISHING,
            question = "An email claims you won a ₹50,000 cashback prize and gives you 10 minutes to claim it at 'bank-reward-claim[.]example'. What red flags are present?",
            options = listOf(
                "Unexpected reward, artificial urgency, and a lookalike unverified domain",
                "Only the use of Indian Rupee symbol",
                "No red flags if the email has a bank logo",
                "It is safe because it did not ask for an attachment"
            ),
            correctIndex = 0,
            explanation = "Scammers routinely pair fake logos with sudden prizes, tight countdowns, and lookalike domains to steal credentials."
        ),
        QuizQuestion(
            id = 4,
            category = ThreatModuleId.PHISHING,
            question = "A courier SMS says your parcel is on hold and asks you to pay ₹5 using a link. Why is a ₹5 request dangerous?",
            options = listOf(
                "It is harmless because ₹5 is negligible",
                "The fake payment page captures your card number, CVV, and OTP for large unauthorized debits",
                "India Post only accepts ₹10 fees",
                "Courier companies never send SMS alerts"
            ),
            correctIndex = 1,
            explanation = "The ₹5 lure lowers victim caution so they type full card or netbanking credentials into a phishing portal."
        ),
        QuizQuestion(
            id = 5,
            category = ThreatModuleId.PHISHING,
            question = "What is 'Vishing'?",
            options = listOf(
                "Phishing conducted through voice phone calls impersonating trusted officials",
                "Scanning QR codes at shopping malls",
                "Updating software from Play Store",
                "Using two-factor authentication"
            ),
            correctIndex = 0,
            explanation = "Vishing (Voice Phishing) uses persuasive or intimidating phone calls to extract OTPs, PINs, or remote access."
        ),
        QuizQuestion(
            id = 6,
            category = ThreatModuleId.PHISHING,
            question = "You receive an SMS warning your electricity will be disconnected at 9:30 PM tonight and telling you to call an unknown 10-digit mobile number. What is this?",
            options = listOf(
                "Standard automated power board alert",
                "A common urgency-based phishing/vishing scam",
                "A legal court notice",
                "A mandatory smart-meter upgrade"
            ),
            correctIndex = 1,
            explanation = "Utility boards do not threaten sudden night disconnections asking citizens to call personal mobile numbers."
        ),
        QuizQuestion(
            id = 7,
            category = ThreatModuleId.DIGITAL_ARREST,
            question = "Which statement about 'Digital Arrest' under Indian law is true?",
            options = listOf(
                "Police can place citizens under arrest over Skype if a warrant PDF is shown",
                "Law enforcement agencies NEVER conduct arrests over video calls or demand money to settle cases",
                "RBI requires an escrow deposit before bail",
                "Only CBI officers are allowed to conduct video-call arrests"
            ),
            correctIndex = 1,
            explanation = "There is no provision for 'Digital Arrest' in India. Any video call claiming arrest and demanding payment is a fraud."
        ),
        QuizQuestion(
            id = 8,
            category = ThreatModuleId.DIGITAL_ARREST,
            question = "During a threatening video call from a 'police officer', why do scammers tell you NOT to disconnect or speak to your family?",
            options = listOf(
                "To comply with courtroom audio rules",
                "To keep you isolated and panicked so nobody alerts you that it is a scam",
                "Because WhatsApp blocks group calls",
                "To protect your mobile battery"
            ),
            correctIndex = 1,
            explanation = "Psychological isolation prevents victims from verifying facts with family members or local police."
        ),
        QuizQuestion(
            id = 9,
            category = ThreatModuleId.DIGITAL_ARREST,
            question = "What should you do if someone in a police uniform on WhatsApp video accuses you of money laundering and demands an immediate transfer?",
            options = listOf(
                "Transfer half the amount as a goodwill gesture",
                "Stay on camera until your bank opens",
                "End the call immediately, make no payments, and verify/report via 1930 or your local police station",
                "Email them a copy of your Aadhaar card"
            ),
            correctIndex = 2,
            explanation = "Uniforms, office backgrounds, and PDF warrants on video calls are easily faked. Hang up immediately and report via 1930."
        ),
        QuizQuestion(
            id = 10,
            category = ThreatModuleId.DIGITAL_ARREST,
            question = "An automated call claims TRAI will block your SIM in 2 hours due to illegal activity and asks you to press 9. What is the safest response?",
            options = listOf(
                "Press 9 and share your Aadhaar to clear your SIM",
                "Hang up and report the scam call—TRAI does not threaten SIM blocks via automated extortion calls",
                "Pay the verification fee immediately",
                "Turn off your phone for 24 hours"
            ),
            correctIndex = 1,
            explanation = "Fake TRAI/Customs IVR calls are the opening hook used to funnel victims into Digital Arrest video calls."
        ),
        QuizQuestion(
            id = 11,
            category = ThreatModuleId.DIGITAL_ARREST,
            question = "Which national helpline number should Indian citizens call immediately if they suffer financial cyber fraud?",
            options = listOf(
                "1930",
                "1800-000-111",
                "1444",
                "1212"
            ),
            correctIndex = 0,
            explanation = "1930 is the official National Cybercrime Helpline in India, alongside the official portal https://cybercrime.gov.in/."
        ),
        QuizQuestion(
            id = 12,
            category = ThreatModuleId.MALICIOUS_APPS,
            question = "Why is a simple calculator app requesting Camera, Contacts, Microphone, and SMS permissions suspicious?",
            options = listOf(
                "Because calculators require SMS to compute percentages",
                "Those permissions are unrelated to arithmetic and could be used to harvest contacts and intercept OTPs",
                "Android requires every app to ask for Contacts",
                "It improves battery efficiency"
            ),
            correctIndex = 1,
            explanation = "Unnecessary permissions are a prime indicator of spyware or data-harvesting apps. Always apply the principle of least privilege."
        ),
        QuizQuestion(
            id = 13,
            category = ThreatModuleId.MALICIOUS_APPS,
            question = "Someone sends a file named 'Traffic_eChallan.apk' or 'Wedding_Invitation.apk' on WhatsApp. What should you do?",
            options = listOf(
                "Install it quickly to check the fine",
                "Enable 'Install unknown apps' so it opens faster",
                "Never install APK files from messaging apps—delete it and verify fines only on official government portals",
                "Forward it to check if others can open it"
            ),
            correctIndex = 2,
            explanation = "Malicious APKs sent via WhatsApp often steal SMS OTPs and banking credentials once sideloaded."
        ),
        QuizQuestion(
            id = 14,
            category = ThreatModuleId.MALICIOUS_APPS,
            question = "Why is granting 'Accessibility Service' permission to an unverified app especially dangerous?",
            options = listOf(
                "It changes your wallpaper automatically",
                "It lets the app read screen content, capture typed PINs, and simulate taps inside banking apps",
                "It disables Bluetooth permanently",
                "It deletes your Ringtone folder"
            ),
            correctIndex = 1,
            explanation = "Accessibility services were designed to assist users with disabilities, so banking Trojans abuse them for automated fraud."
        ),
        QuizQuestion(
            id = 15,
            category = ThreatModuleId.MALICIOUS_APPS,
            question = "A fake customer-care caller asks you to install a screen-sharing app to 'verify your KYC refund'. What is the real risk?",
            options = listOf(
                "Screen-sharing slows down Wi-Fi speed",
                "The scammer can watch your screen in real time to see OTPs, passwords, and guide you into sending money",
                "Screen-sharing automatically updates your phone OS",
                "There is no risk if you mute your microphone"
            ),
            correctIndex = 1,
            explanation = "Legitimate customer support never needs live remote screen-sharing to process refunds or KYC."
        ),
        QuizQuestion(
            id = 16,
            category = ThreatModuleId.MALICIOUS_APPS,
            question = "What is the primary threat posed by unverified 'instant loan' apps asking for Contacts and Photo Gallery access?",
            options = listOf(
                "They automatically improve your credit score",
                "They harvest contacts and private photos to blackmail and harass victims and their families",
                "They only back up photos to your SD card",
                "They provide lower interest rates"
            ),
            correctIndex = 1,
            explanation = "Predatory fake loan apps weaponize stolen contacts and photos for extortion even after borrowers pay back inflated amounts."
        )
    )

    // ==================== 5. RESOURCE CENTRE GUIDES ====================

    val resourceGuides = listOf(
        CyberResourceGuide(
            id = "passwords",
            title = "Password Security",
            summary = "Strong, unique passwords stop credential stuffing across accounts.",
            actionPoints = listOf(
                "Use at least 12–16 characters combining letters, numbers, and symbols.",
                "Never reuse your banking or primary email password on other websites.",
                "Avoid birthdays, phone numbers, or family names in passwords."
            )
        ),
        CyberResourceGuide(
            id = "2fa",
            title = "Two-Factor Authentication (2FA)",
            summary = "Adds a second verification barrier even if your password leaks.",
            actionPoints = listOf(
                "Enable 2FA on email, social media, and financial accounts.",
                "Prefer authenticator apps or hardware security keys over SMS where available.",
                "Never approve unexpected 2FA login prompts you did not initiate."
            )
        ),
        CyberResourceGuide(
            id = "upi",
            title = "UPI Safety",
            summary = "Protect instant mobile payments from collect and QR scams.",
            actionPoints = listOf(
                "Remember: UPI PIN is ONLY for paying out—never for receiving money or cashback.",
                "Always read the full recipient name and amount before entering your PIN.",
                "Set sensible daily UPI transaction limits in your bank app."
            )
        ),
        CyberResourceGuide(
            id = "browsing",
            title = "Safe Browsing Practices",
            summary = "Avoid spoofed websites and malicious search-engine ads.",
            actionPoints = listOf(
                "Type official bank URLs directly or use verified bookmarks instead of search ads.",
                "Do not rely blindly on the padlock icon—verify the exact spelling of the domain.",
                "Avoid downloading software cracks or unverified files from pop-up pages."
            )
        ),
        CyberResourceGuide(
            id = "social",
            title = "Social Media Privacy",
            summary = "Limit personal details scammers use for impersonation.",
            actionPoints = listOf(
                "Keep profiles private and hide sensitive personal details like birth date and phone number.",
                "Be cautious of fake friend requests or urgent money pleas from cloned accounts.",
                "Verify money requests from friends over a direct phone call first."
            )
        ),
        CyberResourceGuide(
            id = "smartphone",
            title = "Smartphone Security",
            summary = "Harden your Android device against spyware and SIM abuse.",
            actionPoints = listOf(
                "Use a strong screen lock (PIN/biometric) and enable SIM card PIN lock.",
                "Keep 'Install unknown apps' turned OFF for WhatsApp, browsers, and file managers.",
                "Keep Google Play Protect active and install OS updates promptly."
            )
        ),
        CyberResourceGuide(
            id = "banking",
            title = "Online Banking Safety",
            summary = "Safe habits for netbanking, cards, and account alerts.",
            actionPoints = listOf(
                "Enable instant SMS and email alerts for every debit transaction.",
                "Turn off international usage or set low limits on cards when not travelling.",
                "Never access netbanking over shared public computers or untrusted Wi-Fi."
            )
        ),
        CyberResourceGuide(
            id = "public_wifi",
            title = "Public Wi-Fi & Device Hygiene",
            summary = "Reduce exposure when travelling or studying on campus.",
            actionPoints = listOf(
                "Avoid banking or entering passwords on open airport, cafe, or railway Wi-Fi.",
                "Turn off auto-connect to open Wi-Fi networks and Bluetooth when unused.",
                "Periodically audit installed apps and remove ones you no longer recognize."
            )
        )
    )
}
