# EDIDS Project

# UML
<img src="https://www.plantuml.com/plantuml/png/hLVDSkCs3BxxATYRCjxCla0chUDaDvawgQT9TplJsGCjOMMEPKabAM_SdNtt0YJ57qbs3kt5bW0Gm8SV0lf9Rs4FJ49HI53gik1ojwawcCr4hK5-vJcmHTjM8kTQDFMoGUghddsViVcX4GMh6bvaprgAkdp7dZKluI9XVUHxY1ZqoahGz9yHOIsR5gGMe8WStf3v8_RXxkzEj7nJ0PbcHZyGwRSwkwkrqCU1_1yg5UuKd19TDlizhuiWgq0lPAaShB--e9-iVoOlNH38Vt-WfurZHL9ZwgGUU8Myqs2nvnLNgdTkpQK2QLoyL1eLvIc7YHh9TGuaC64jTtb3xZrNsqVUNlL9MvATgv_Hk8Qwy1QlCN_DhcjVJ1mPcq-QHHGFphA1OJCleHGAm-jaNI3P3evdpENsmV2DgfEINNtQW3rfoQmb9tkcqszYopTAIvvZN4oG7pJiARQNJhJ6wiIt1unPYI2_WIYt6BivoJbrLBjE6cFmr9TpVmHfdysfpBU3yYGdDEN9isxayQaf87P7SudwFWduPemwYKb7NhneDgNUrh_H_L0Ve2PTM3hk2QatJLC1hncln06C1rmhHgbN1br_nkJTlIGG0zK_4RlNY-SMf3evOobEnE8XgoXh7XBP0MKBl0hnTCI2x_6wEHVcf_NIbrKK4nV70hxohjAtWbTDcOJtDkOaVgMSFZM-ZNtMLgBU6LjzHAectq61X4LE3Sku-GlEO4r7PbP6EevL55d7J49hj6NsZnZEIkbPiVIlS9oiAg97OEZoDXW-IjvkhposTodCQrQYe94nS1oBvPsKM1ccocDwA-4Wcavv1YQHIgy4DpYChs_QjCF5Y6KNSSEOAxUJu6mdo9V1fymV6DHTIImtnwnpPKydqiObOilLEa8Ip62VOLiLNCE8d_WuuFgmtzMmF_gonHB670JkGckUx-Y6yYBcHUpR2YTyZGsLp51_k_6Y45G9lB8ga7NRT2Z5k7b_nB8ME51m_2Jtss9mBgsx8JFgKQUTL0mCf2g6J7ik2WZs7E3W-K-OdOu0kh24jr6xaoTWzzn9tBT36yEhPC0T8VTtgq4LfPxxHq8KnQS8pyXPuVIyekTh9l1NHQ7PyzVFRjvvcklOdZwF328Q7s8p7WtIo720wzbeKdBqFhjWxz-tjBnO7y8GeVSiE3cOh8-zYqdO0ZMTsuF3onXBK80LOVoZdp18S1qTKytUQSWUUPIQ5yaBaF60iPtAvb7GDGbbmon4MjOLFwA_fsUKNL6cLf0-7eOmCOXebCUzi6tJxD_7xnkkm5yBCT7tJHpR-pORfsfClF5AfpXJVOKwXaFUAvzWH0PES4VjwGFqUzE4ssHo7NFReLyML2v5wuRsH6CP4jR0DQlRN4MJ7nTIlAOq3VyvMOq-eyvicF4ouYsVJdnyhGJnm8qSBll6ktYZ5GCYSWn1fUQ9u4K6AygonvnENq_rYytqvlHzl2UTtsW6-q-ObeOG9TMgUx9TvKvDvM7XvUBHFFxYmY_PWoZeRWkpQ2nby9mvwp_gBks7itFlv0c3_On2Id7uMli_lk2JlY5pigaga0Jibmz_FgxOYc-WwYS82L47T5fZoZROeFOHQPzkcbUsQLvdvxzV-rKAnomkK_z4E3RsFldnBm00" />


# Property configuration

In project resources copy "copy_application.conf" in "application.conf" and then populate AWS keys, secret and region.
"copy_application.conf" is just a template.


# S3 configuration

## Create Policy with minimal privileges

<pre>
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "ListObjectsInBucket",
            "Effect": "Allow",
            "Action": ["s3:ListBucket"],
            "Resource": ["arn:aws:s3:::edidstextgame"]
        },
        {
            "Sid": "AllObjectActions",
            "Effect": "Allow",
            "Action": "s3:*Object",
            "Resource": ["arn:aws:s3:::edidstextgame/*"]
        }
    ]
}
</pre>

## Create group

Create a group and assign the created policy to the group

## Create a user

Create a user and assign the user to the group
The user will inherit group permissions.

## Generate credentials

Generate credentials and copy secret id and value in application.conf