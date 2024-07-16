#!/usr/bin/env python3

import os
import argparse
import requests
import logging
from PIL import Image, ImageDraw, ImageOps
from requests.auth import HTTPBasicAuth

def download_icon(icon_url, fallback_icon_url, output_path, auth=None):
  """
  Download icon. If the download fails, use the fallback icon.

  :param icon_url: URL to the icon
  :param fallback_icon_url: URL to the fallback icon
  :param output_path: Path to save the downloaded icon
  :param auth: Optional tuple for basic authentication
  """
  def _download(url):
    try:
      response = requests.get(url, auth=auth)
      response.raise_for_status()
      return response.content
    except requests.RequestException as e:
      logging.error(f"Failed to download from '{url}': {e}")
      return None

  icon_content = _download(icon_url)

  if icon_content is None:
    logging.info(f"Attempting to download fallback icon from '{fallback_icon_url}'...")
    icon_content = _download(fallback_icon_url)

    if icon_content is None:
      logging.error(f"Both icon and fallback download failed. Exiting.")
      exit(1)

  with open(output_path, 'wb') as f:
    f.write(icon_content)

  logging.info(f"Icon downloaded successfully and saved to '{output_path}'.")

def generate_launcher_icons(res_directory, icon_path):
  """
  Generate launcher icons in WEBP format.

  :param res_directory: Path to the resources directory
  :param icon_path: Path to the icon file
  """
  if not os.path.exists(icon_path):
    logging.error(f"Icon file not found: {icon_path}. Exiting.")
    exit(1)

  if not os.path.isdir(res_directory):
    logging.error(f"Invalid resource directory: {res_directory}. Exiting.")
    exit(1)

  icon_mappings = {
    "mipmap-hdpi": "72x72",
    "mipmap-mdpi": "96x96",
    "mipmap-xhdpi": "128x128",
    "mipmap-xxhdpi": "144x144",
    "mipmap-xxxhdpi": "192x192"
  }

  def generate_webp(icon_path, size, output_path, round_icon=False):
    """
    Generate a WEBP icon.

    :param icon_path: Path to the icon file
    :param size: Size of the icon in format 'widthxheight' (e.g., '72x72')
    :param output_path: Path to save the generated icon
    :param round_icon: Whether to generate a round icon (default: False)
    """
    img = Image.open(icon_path)
    img = img.resize((int(size.split('x')[0]), int(size.split('x')[1])), Image.LANCZOS)

    if round_icon:
      bigsize = (img.size[0] * 3, img.size[1] * 3)
      mask = Image.new('L', bigsize, 0)
      draw = ImageDraw.Draw(mask)
      draw.ellipse((0, 0) + bigsize, fill=255)
      mask = mask.resize(img.size, Image.LANCZOS)
      img.putalpha(mask)

    img.save(output_path, 'WEBP', quality=100)

  for directory, size in icon_mappings.items():
    output_icon_directory = os.path.join(res_directory, directory)
    os.makedirs(output_icon_directory, exist_ok=True)

    output_path = os.path.join(output_icon_directory, "ic_launcher.webp")
    generate_webp(icon_path, size, output_path, round_icon=False)

    round_output_path = os.path.join(output_icon_directory, "ic_launcher_round.webp")
    generate_webp(icon_path, size, round_output_path, round_icon=True)

  logging.info(f"Launcher icons generated successfully and saved inside '{res_directory}'.")

def generate_splash_icon(res_directory, icon_path):
  """
  Generate splash screen icon following splash screen API guidelines.

  :param res_directory: Path to the resources directory
  :param icon_path: Path to the icon file
  """
  if not os.path.exists(icon_path):
    logging.error(f"Icon file not found: {icon_path}. Exiting.")
    exit(1)

  if not os.path.isdir(res_directory):
    logging.error(f"Invalid resource directory: {res_directory}. Exiting.")
    exit(1)

  output_directory = os.path.join(res_directory, "drawable")
  os.makedirs(output_directory, exist_ok=True)

  output_path = os.path.join(output_directory, "splash_icon.png")

  img = Image.open(icon_path).convert("RGBA")
  img = ImageOps.expand(img, border=380, fill=(0, 0, 0, 0))
  img = img.resize((512, 512), Image.LANCZOS)
  img.save(output_path, 'PNG', quality=100)

  logging.info(f"Splash icon generated successfully and saved to '{output_path}'.")

def generate_app_icon(res_directory, icon_path):
  """
  Generate icon for app use (e.g., for pages and utilities)

  :param res_directory: Path to the resources directory
  :param icon_path: Path to the icon file
  """
  if not os.path.exists(icon_path):
    logging.error(f"Icon file not found: {icon_path}. Exiting.")
    exit(1)

  if not os.path.isdir(res_directory):
    logging.error(f"Invalid resource directory: {res_directory}. Exiting.")
    exit(1)

  output_directory = os.path.join(res_directory, "drawable")
  os.makedirs(output_directory, exist_ok=True)

  output_path = os.path.join(output_directory, "app_icon.png")

  img = Image.open(icon_path)
  img = img.resize((512, 512), Image.LANCZOS)
  img.save(output_path, 'PNG', quality=100)

  logging.info(f"App icon generated successfully and saved to '{output_path}'.")

def process_icons(res_directory, launcher_icon_url, splash_icon_url, app_icon_url, fallback_icon_url, auth):
  """
  Process the icons by downloading and generating them.

  :param res_directory: Path to the resources directory
  :param launcher_icon_url: URL to the launcher icon
  :param splash_icon_url: URL to the splash screen icon
  :param app_icon_url: URL to the app icon
  :param fallback_icon_url: URL to the fallback icon
  :param auth: Optional HTTPBasicAuth object
  """
  icon_urls = {
    'launcher': launcher_icon_url,
    'splash': splash_icon_url,
    'app': app_icon_url
  }

  for icon_type, url in icon_urls.items():
    tmp_path = f"{icon_type}-icon@tmp.png"
    logging.info(f"Starting download of {icon_type} icon...")
    download_icon(url, fallback_icon_url, tmp_path, auth=auth)

    if icon_type == 'launcher':
      generate_launcher_icons(res_directory, tmp_path)
    elif icon_type == 'splash':
      generate_splash_icon(res_directory, tmp_path)
    elif icon_type == 'app':
      generate_app_icon(res_directory, tmp_path)

    os.remove(tmp_path) if os.path.exists(tmp_path) else None

def main():
  logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

  parser = argparse.ArgumentParser(description="Generate icon assets for the app")
  parser.add_argument('--res-directory', type=str, required=True, help='Path to the resources directory')
  parser.add_argument('--launcher-icon-url', type=str, required=True, help='URL to the launcher icon')
  parser.add_argument('--splash-icon-url', type=str, required=True, help='URL to the splash screen icon')
  parser.add_argument('--app-icon-url', type=str, required=True, help='URL to the app icon')
  parser.add_argument('--fallback-icon-url', type=str, required=True, help='URL to the fallback icon')
  parser.add_argument('--auth-username', type=str, help='Username for basic authentication')
  parser.add_argument('--auth-password', type=str, help='Password for basic authentication')
  args = parser.parse_args()

  auth = None
  if args.auth_username and args.auth_password:
    auth = HTTPBasicAuth(args.auth_username, args.auth_password)

  process_icons(
    res_directory=args.res_directory,
    launcher_icon_url=args.launcher_icon_url,
    splash_icon_url=args.splash_icon_url,
    app_icon_url=args.app_icon_url,
    fallback_icon_url=args.fallback_icon_url,
    auth=auth
  )

if __name__ == "__main__":
  main()